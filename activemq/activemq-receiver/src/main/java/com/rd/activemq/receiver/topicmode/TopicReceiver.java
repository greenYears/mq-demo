package com.rd.activemq.receiver.topicmode;

import com.rd.support.activemq.constants.ActivemqConstant;
import com.rd.support.activemq.core.ActivemqCallback;
import com.rd.support.activemq.manager.ActivemqManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Created by BG297869 on 2017/7/3.
 */
@Component
@Slf4j
@EnableAsync
public class TopicReceiver {
    @Autowired
    private ActivemqManager activemqManager;

    @Async
    public void receiveMessage() {
        log.info("[x - 1]开始接收消息");
        activemqManager.receiveTopicMessage(ActivemqConstant.MODE_TOPIC_NAME, new ActivemqCallback() {
            @Override
            public void receiveCallback(Message message) throws JMSException {
                log.info("[x - 1]接收到的消息是 {}", message);
            }

            @Override
            public void sendCallback(Message message) throws JMSException {

            }
        });
    }

    @Async
    public void receiveMessage2() {
        log.info("[x - 2]开始接收消息");
        activemqManager.receiveTopicMessage(ActivemqConstant.MODE_TOPIC_NAME, new ActivemqCallback() {
            @Override
            public void receiveCallback(Message message) throws JMSException {
                log.info("[x - 2]接收到的消息是 {}", message);
            }

            @Override
            public void sendCallback(Message message) throws JMSException {

            }
        });
    }
}
