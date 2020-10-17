package com.project.canal.web.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.project.canal.web.module.po.LogPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CanalServerUtil {

    /**
     * @param schema 需要监控的库名
     **/
    public static void readLog(CanalConnector connector){

        int batchSize = 1000;
        try {
            // 获取指定数量的数据
            Message message = connector.getWithoutAck(batchSize);
            long batchId = message.getId();
            int size = message.getEntries().size();
            if (batchId == -1 || size == 0) {
                //无操作
            } else {
                List<LogPO> result = printEntry(message.getEntries());
                System.out.println("logInfo====>"+ JSONObject.toJSONString(result));
            }

            // 提交确认
            connector.ack(batchId);
            // connector.rollback(batchId); // 处理失败, 回滚数据
        } catch (Exception e){
            log.error(" binglong处理失败，error={}",e);
        }

    }

    /**
     * 解析binlog
     **/
    private static List<LogPO> printEntry(List<CanalEntry.Entry> entrys) {
        List<LogPO> result = new ArrayList<LogPO>();

        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                log.error(" binlog parser failed, data:{}",entry.toString());
                continue;
            }

            CanalEntry.EventType eventType = rowChage.getEventType();
            /*System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));*/

            LogPO logPO = new LogPO();
            logPO.setLogId(entry.getHeader().getLogfileOffset());
            logPO.setTableName(entry.getHeader().getTableName());
            logPO.setBusinessType(eventType);

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                List<LogPO.TableInfo> infos = new ArrayList<LogPO.TableInfo>();
                if (eventType == CanalEntry.EventType.DELETE) {
                    infos = printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    infos = printColumn(rowData.getAfterColumnsList());
                } else if (eventType == CanalEntry.EventType.UPDATE) {
                    infos = printColumn(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------> before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------> after");
                    printColumn(rowData.getAfterColumnsList());
                }

                logPO.setInfos(infos);
                result.add(logPO);
            }
        }
        return result;
    }

    /**
     * 组装更新信息
     **/
    private static List<LogPO.TableInfo> printColumn(List<CanalEntry.Column> columns) {
        List<LogPO.TableInfo> result = new ArrayList<LogPO.TableInfo>();
        for (CanalEntry.Column column : columns) {
            LogPO.TableInfo info = new LogPO.TableInfo();
            info.setColumnName(column.getName());
            info.setColumnValue(column.getValue());
            result.add(info);
        }
        return result;
    }

}
