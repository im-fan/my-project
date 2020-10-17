package com.project.canal.web.service;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.project.canal.web.util.CanalServerUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Slf4j
@Configuration
public class SapSystemCanalService implements Job {

    private CanalConnector connectorSap = null;

    private final String ip = "192.168.154.231";
    private final Integer port = 11111;
    private final String userName = "admin";
    private final String password = "4ACFE3202A5FF5CF467898FC58AAB1D615029441";
    private final String sapSystemSchema = "sap_system";
    private final String sapSystemDestination = "example";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        if(connectorSap == null){
            connectorSap = CanalConnectors.newSingleConnector(new InetSocketAddress(ip,
                    port), sapSystemDestination, userName, password);
        }

        try {
            connectorSap.connect();
            connectorSap.subscribe(sapSystemSchema+"\\..*");
            connectorSap.rollback();

            CanalServerUtil.readLog(connectorSap);
        } catch (Exception e) {
            log.error("{} canal连接失败，error={}",sapSystemSchema,e);
        } finally {
            connectorSap.disconnect();
        }
    }
}
