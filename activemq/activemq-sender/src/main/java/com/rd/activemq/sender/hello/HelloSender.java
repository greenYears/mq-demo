package com.rd.activemq.sender.hello;

import com.google.common.collect.Lists;
import com.rd.support.activemq.core.ActivemqCallback;
import com.rd.support.activemq.manager.ActivemqManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by BG297869 on 2017/7/3.
 */
@Component
@EnableScheduling
@Slf4j
public class HelloSender {

    /**
     * 队列名称
     */
    public static final String SEND_QUEUE_NAME = "mq.active.hello";

    @Autowired
    private ActivemqManager activemqManager;

    @Scheduled(fixedDelay = 60000, initialDelay = 1000)
    public void helloSend() {
        List<String> messageList = Lists.newArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 3; i++) {
            messageList.add("[" + sdf.format(new Date()) + "] 消息【" + i + "】");
        }

        activemqManager.sendQueueMessage(SEND_QUEUE_NAME, messageList, new ActivemqCallback() {
            @Override
            public void receiveCallback(Message message) throws JMSException {

            }

            @Override
            public void sendCallback(Message message) throws JMSException {
                log.info("[x - 1] 发送的消息是 {}", message);
            }
        });
    }
}
