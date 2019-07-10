package com.project.web.rocketmq;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConsumerConfig implements CommandLineRunner {

    @Value("${rocketmq.consumer.groupName}")
    private String groupName;

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.topics}")
    private String topics;

    @Override
    public void run(String... strings) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);

        /** 多个用;分割 **/
        consumer.setNamesrvAddr(namesrvAddr);

        /** 配置topic和tag，subExpression=* 表示所有tag，多个tag tag1 || tag2 || tag3 **/
        consumer.subscribe(topics, "*");

        /** 并发消费-默认消费方式 **/
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            String date = DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SS");
            System.out.printf("%s %s Receive New Messages: %s %n",date, Thread.currentThread().getName(), String.valueOf(msgs));
            boolean flag = false;
            if(flag){
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

        });

        consumer.start();

        System.out.println("consumer Started=====>");
    }
}
