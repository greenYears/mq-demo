package com.rd.activemq.receiver.hello;

import com.rd.support.activemq.core.ActivemqCallback;
import com.rd.support.activemq.manager.ActivemqManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Created by BG297869 on 2017/7/3.
 */
@Component
@Slf4j
@EnableAsync
public class HelloReceiver {
    @Autowired
    private ActivemqManager activemqManager;
    /**
     * 队列名称
     */
    public static final String SEND_QUEUE_NAME = "mq.active.hello";

    /**
     * 接收消息
     */
    @Async
    public void receiverMessage() {
        log.info("开始[x - 1]接收消息");
        activemqManager.receiverQueueMessage(SEND_QUEUE_NAME, new ActivemqCallback() {
            @Override
            public void receiveCallback(Message message) throws JMSException {
                if (message != null) {
                    TextMessage msg = (TextMessage) message;
                    log.info("[x - 1] 接收到的消息是 {}", msg.getText());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    log.info("[x - 1]没有接收到消息");
                }
            }

            @Override
            public void sendCallback(Message message) throws JMSException {

            }
        });


    }

    @Async
    public void receiverMessage2() {
        log.info("开始[x - 2]接收消息");
        activemqManager.receiverQueueMessage(SEND_QUEUE_NAME, new ActivemqCallback() {
            @Override
            public void receiveCallback(Message message) throws JMSException {
                if (message != null) {
                    TextMessage msg = (TextMessage) message;
                    log.info("[x - 2] 接收到的消息是 {}", msg.getText());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    log.info("[x - 2]没有接收到消息");
                }
            }

            @Override
            public void sendCallback(Message message) throws JMSException {

            }
        });


    }
}
