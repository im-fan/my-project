package com.project.canal.web.service;


import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.project.canal.web.util.CanalServerUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Slf4j
@Configuration
public class CanalManagerCanalService implements Job {

    private CanalConnector connector;
    private final String ip = "192.168.154.231";
    private final Integer port = 11111;
    private final String userName = "admin";
    private final String password = "4ACFE3202A5FF5CF467898FC58AAB1D615029441";
    private final String canalManageSchema = "canal_manager";
    private final String canalManageDestination = "example2";


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            if(connector == null){
                connector = CanalConnectors.newSingleConnector(new InetSocketAddress(ip,
                                port), canalManageDestination, userName, password);
            }

            connector.connect();
            connector.subscribe(canalManageSchema+"\\..*");
            connector.rollback();

            CanalServerUtil.readLog(connector);

        } catch (Exception e) {
            log.error("{} canal连接失败，error={}",canalManageSchema,e);
        } finally {
            connector.disconnect();
        }

    }

}
