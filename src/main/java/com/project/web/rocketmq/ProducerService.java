
package com.project.web.rocketmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消息发送方法
 *
 *@author: Weiyf
 *@Date: 2019-07-10 17:54
 */
@Service
@Slf4j
public class ProducerService {

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @Value("${rocketmq.consumer.topics}")
    private String topics;

    /** 发送消息调用方法 **/
    private void sendMsg(Object obj){

        try{
            String tag = "TagA";
            DefaultMQProducer producer = new DefaultMQProducer(groupName);

            /** 指定nameServer地址 ip:prot **/
            producer.setNamesrvAddr(namesrvAddr);
            producer.start();

            String msg = "normal message body ======>";
            /** 发送同步消息 **/
            defaultMsg(producer,topics,tag,msg);

            //关闭producer
            producer.shutdown();

        } catch (MQClientException e) {
            log.error("message send failed, error={}",JSONObject.toJSONString(e));
        }
    }

    /**
     * 批量发送消息
     * 支持
     *@author: Weiyf
     *@Date: 2019-07-01 15:03
     */
    private void batchMsg(DefaultMQProducer producer) {
        try {
            List<Message> messageList = new ArrayList<>();
            //设置失败后重试次数
            int count = 10;
            for (int i = 0; i < count; i++) {
                String topic = "TopicA";
                String tag = "TagA"+i;

                Message msg = new Message(topic,
                        tag,
                        (String.format("Hello RocketMQ %s",i)).getBytes(RemotingHelper.DEFAULT_CHARSET)
                );

                //SQL92方式过滤消息
                msg.putUserProperty("id",String.valueOf(i));

                messageList.add(msg);
            }
            //设置发送消息超时时长-不设置会导致发送失败
            producer.setSendMsgTimeout(1000);
            SendResult result = producer.send(messageList);
            String date = DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SS");
            System.out.printf("批量消息发送完毕===>data=%s,result=%s\n",date, JSONObject.toJSONString(result));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 批量发送消息
     * 支持sql过滤
     *@author: Weiyf
     *@Date: 2019-07-01 15:03
     */
    private void asyncMsg(DefaultMQProducer producer) {
        try {
            //设置失败后重试次数
            producer.setRetryTimesWhenSendAsyncFailed(0);
            int count = 10;
            final CountDownLatch2 countDownLatch2 = new CountDownLatch2(count);
            for (int i = 0; i < count; i++) {
                String topic = "TopicA";
                String tag = "TagA"+i;
                Message msg = new Message(topic,
                        tag,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                );
                //18个级别(1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h)，超出默认最大级别
                msg.setDelayTimeLevel(2);

                //SQL92方式过滤消息
                msg.putUserProperty("id",String.valueOf(i));

                //设置发送消息超时时长-不设置会导致发送失败
                producer.setSendMsgTimeout(6000);

                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch2.countDown();
                        String date = DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SS");
                        System.out.printf("异步消息发送成功，time=%s,msgId=%s\n",date,sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable e) {
                        countDownLatch2.countDown();
                        String date = DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SS");
                        System.out.printf("异步消息发送失败,time=%s,error=%s\n",date,e);
                    }
                });

                String date = DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SS");
                System.out.printf("%s %s%n", date,msg);

            }

            //等待所有消息都消费完成后才关闭
            countDownLatch2.await(30,TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 只管发送，不关心消费结果
     * 支持sql表达式过滤
     *@author: Weiyf
     *@Date: 2019-07-01 15:03
     */
    private void oneWayMsg(DefaultMQProducer producer) {
            for (int i = 0; i < 10; i++) {
                try {

                    String topic = "TopicA";
                    String tag = "TagA" + i;
                    String brokerName = "broker-a";
                    Message msg = new Message(topic,
                            tag,
                            ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                    );

                    //SQL92方式过滤消息
                    msg.putUserProperty("id",String.valueOf(i));
                    //发送消息超时时长 毫秒
                    producer.setSendMsgTimeout(6000);

                    producer.sendOneway(msg, new MessageQueue(topic, brokerName, 0));

                    String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SS");
                    System.out.printf("%s %s%n", date, msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    }
    /**
     * 默认消息类型 同步
     * 支持sql表达式过滤
     *@author: Weiyf
     *@Date: 2019-07-01 15:00
     */
    private void defaultMsg(DefaultMQProducer producer,
                                   String topic,
                                   String tag,
                                   String msgBody) {

       try {
            //SQL92方式过滤消息
            //msg.putUserProperty("id",String.valueOf(i));

            //发送消息超时时长 毫秒
            producer.setSendMsgTimeout(6000);

            Message msg = new Message(topic,
                    tag,
                    msgBody.getBytes(RemotingHelper.DEFAULT_CHARSET));

            //默认同步
            SendResult sendResult = producer.send(msg);

            String date = DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SS");
            System.out.printf("%s %s%n", date,sendResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 顺序消息
     *@author: Weiyf
     *@Date: 2019-07-01 15:00
     */
    private void queueMsg(DefaultMQProducer producer) {

        for (int i = 0; i < 10; i++) {
            try {
                String date = DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss.SS");

                String topic = "TopicA";
                String tag = "TagA"+i;
                byte[] body = (date + "Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET);
                Message msg = new Message(topic, tag,body );

                //18个级别(1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h)，超出默认最大级别
                msg.setDelayTimeLevel(2);

                //发送消息超时时长 毫秒
                producer.setSendMsgTimeout(6000);
                //顺序消息，消息内容-消息集合选择器-选择器参数
                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        //分给2个消息集合
                        int id = (int)arg%2;

                        long index = id % mqs.size();
                        return mqs.get((int) index);
                    }
                },i);

                System.out.println(String.format("SendResult status:%s, queueId:%d  id%d",
                        sendResult.getSendStatus(),
                        sendResult.getMessageQueue().getQueueId(),i%2));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /** 事务消息
     *  不支持延时消息和批量消息
     * **/
    private void transactionMsg(TransactionMQProducer producer) {
        try{

            //发送消息超时时长 毫秒
            producer.setSendMsgTimeout(6000);

            //自定义线程池
            ExecutorService executorService = new ThreadPoolExecutor(
                    2,
                    5,
                    100, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(2000), r -> {
                Thread thread = new Thread(r);
                thread.setName("threadName");
                return thread;
            });
            producer.setExecutorService(executorService);

            AtomicInteger transactionIndex = new AtomicInteger(0);
            ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();
            //添加事务监听器
            producer.setTransactionListener(new TransactionListener(){
                @Override
                public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                    int value = transactionIndex.getAndIncrement();
                    int status = value % 3;
                    localTrans.put(msg.getTransactionId(), status);
                    return LocalTransactionState.UNKNOW;
                }
                @Override
                public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                    Integer status = localTrans.get(msg.getTransactionId());
                    if (null != status) {
                        switch (status) {
                            case 0:
                                return LocalTransactionState.UNKNOW;
                            case 1:
                                return LocalTransactionState.COMMIT_MESSAGE;
                            case 2:
                                return LocalTransactionState.ROLLBACK_MESSAGE;
                        }
                    }
                    return LocalTransactionState.COMMIT_MESSAGE;
                }

            });
            Message msg = new Message("TopicOne", "transactionTag", "这是一条事务消息".getBytes(RemotingHelper.DEFAULT_CHARSET));
            TransactionSendResult sendResult = producer.sendMessageInTransaction(msg,null);
            System.out.printf("发送事务消息结果。%s", JSON.toJSONString(sendResult));
        } catch (MQClientException|UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

}
