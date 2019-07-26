/*
package com.project.web.service.binlog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.project.web.entity.dto.BinlogDto;
import com.project.web.rocketmq.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.Strings;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.web.constant.SqlType.DDL;


*/
/**
 * binlog监听器
 *
 *@author: Weiyf
 *@Date: 2019-07-15 11:44
 *//*

@Service
@Slf4j
public class BinlogClientService implements CommandLineRunner {

    @Value("${binlog.host}")
    private String host;

    @Value("${binlog.port}")
    private int port;

    @Value("${binlog.user}")
    private String user;

    @Value("${binlog.password}")
    private String password;

    @Value("${binlog.serverId}")
    private long serverId;

    */
/** 指定监听的数据表 **//*

    @Value("${binlog.database.table}")
    private String databaseTable;

    */
/** binlog接口 **//*

    private BinaryLogClient client;

    @Autowired
    private ProducerService producerService;

    @Override
    public void run(String... strings) throws Exception {

        MDC.put("logId", Thread.currentThread().getName());

        */
/** binlog监听客户端 **//*

        client = new BinaryLogClient(host,port,user,password);
        client.setServerId(serverId);

        //默认阻塞模式，false会在事件处理完成后关闭
        client.setBlocking(true);

        */
/**数据解释器*//*

        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                */
/**返回1970 经历的毫秒数*//*

                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG
                */
/**CHAR/VARCHAR/BINARY/VARBINARY 返回用byte[]*//*

                //EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        client.setEventDeserializer(eventDeserializer);

        */
/** 注册声明周期监听器 **//*

        //client.registerLifecycleListener();

        //存放表信息
        HashMap<Long, String> tableMap = new HashMap<>();

        */
/** 注册事件监听器 **//*

        client.registerEventListener(event -> {

            */
/** 获取监听的表 **//*

            List<String> tableList = Arrays.asList(databaseTable.split(","));

            //事件数据
            EventData data = event.getData();

            if(data == null){
                log.info("[Binlog监听]，无数据");
                return;
            }

            if(data instanceof TableMapEventData){
                TableMapEventData tableEventMap = (TableMapEventData) data;
                String tableMsg = StringUtils.join(tableEventMap.getDatabase(),
                        ".",tableEventMap.getTable());
                tableMap.put(tableEventMap.getTableId(),tableMsg);
            }

            if(data instanceof QueryEventData){
                QueryEventData queryEventData = (QueryEventData) data;
                String sql = Strings.toUpperCase(queryEventData.getSql());
                if(StringUtils.startsWithAny(sql,DDL)){
                    log.info("[Binlog监听] 表结构变更，sql={}",JSONObject.toJSONString(sql));
                    //发送消息
                    sendMsg(sql);
                    return;
                }
            }

            */
/** 更新记录 **//*

            if(data instanceof UpdateRowsEventData){
                UpdateRowsEventData updateRowsEventData = (UpdateRowsEventData) data;
                String tableName = tableMap.get(updateRowsEventData.getTableId());
                if (tableName != null && tableList.contains(tableName)) {
                    String eventKey = tableName + ".update";
                    for (Map.Entry<Serializable[], Serializable[]> row : updateRowsEventData.getRows()) {
                        String msg = JSON.toJSONString(new BinlogDto(eventKey, row.getValue()));
                        log.info("[Binlog监听]-更新，msg={}", JSONObject.toJSONString(msg));

                        //发送消息
                        sendMsg(msg);
                    }
                }
            }


            */
/** 新增记录 **//*

            else if (data instanceof WriteRowsEventData) {
                WriteRowsEventData writeRowsEventData = (WriteRowsEventData) data;
                String tableName = tableMap.get(writeRowsEventData.getTableId());
                if (tableName != null && tableList.contains(tableName)) {
                    String eventKey = tableName + ".insert";
                    for (Serializable[] row : writeRowsEventData.getRows()) {
                        String msg = JSON.toJSONString(new BinlogDto(eventKey, row));
                        log.info("[Binlog监听]-新增，msg={}", JSONObject.toJSONString(msg));
                        //发送消息
                        sendMsg(msg);
                    }
                }
            }

            */
/** delete数据 **//*

            else if (data instanceof DeleteRowsEventData) {
                DeleteRowsEventData deleteRowsEventData = (DeleteRowsEventData) data;
                String tableName = tableMap.get(deleteRowsEventData.getTableId());
                if (tableName != null && tableList.contains(tableName)) {
                    String eventKey = tableName + ".delete";
                    for (Serializable[] row : deleteRowsEventData.getRows()) {
                        String msg = JSON.toJSONString(new BinlogDto(eventKey, row));
                        log.info("[Binlog监听]-删除，msg={}", JSONObject.toJSONString(msg));
                        //发送消息
                        sendMsg(msg);
                    }
                }
            }
            else{
                log.info("[Binlog监听]-其他操作，不做处理,data={}",JSONObject.toJSONString(data));
            }
        });

        client.connect();
    }

    */
/** 发送消息 **//*

    private void sendMsg(String msg){
        log.info("[binlog] 开始发送mq消息");
        producerService.sendMsg(msg);
        log.info("[binlog] mq消息发送完毕");
    }

}
*/
