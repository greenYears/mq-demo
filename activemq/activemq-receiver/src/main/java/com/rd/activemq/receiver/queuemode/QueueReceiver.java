package com.rd.activemq.receiver.queuemode;

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
import javax.jms.TextMessage;

/**
 * Created by BG297869 on 2017/7/3.
 */
@Component
@Slf4j
@EnableAsync
public class QueueReceiver {
    @Autowired
    private ActivemqManager activemqManager;


    @Async
    public void receiverMessage() {
        activemqManager.receiverQueueMessage(ActivemqConstant.MODE_QUEUE_NAME, new ActivemqCallback() {
            @Override
            public void receiveCallback(Message message) throws JMSException {
                if (message != null) {
                    TextMessage msg = (TextMessage) message;
                    log.info("[x - 1] 接收到的消息是 {}", msg.getText());
                } else {
                    log.info("[x - 1] 没有接收到消息");
                }
            }

            @Override
            public void sendCallback(Message message) throws JMSException {

            }
        });
    }

    @Async
    public void receiverMessage2() {
        activemqManager.receiverQueueMessage(ActivemqConstant.MODE_QUEUE_NAME, new ActivemqCallback() {
            @Override
            public void receiveCallback(Message message) throws JMSException {
                if (message != null) {
                    TextMessage msg = (TextMessage) message;
                    log.info("[x - 2] 接收到的消息是 {}", msg.getText());
                } else {
                    log.info("[x - 2] 没有接收到消息");
                }
            }

            @Override
            public void sendCallback(Message message) throws JMSException {

            }
        });
    }
}
