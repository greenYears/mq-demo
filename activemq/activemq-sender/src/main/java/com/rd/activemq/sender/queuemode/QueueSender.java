package com.rd.activemq.sender.queuemode;

import com.google.common.collect.Lists;
import com.rd.support.activemq.constants.ActivemqConstant;
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
import java.util.UUID;

/**
 * Created by BG297869 on 2017/7/3.
 */
@Component
@EnableScheduling
@Slf4j
public class QueueSender {


    @Autowired
    private ActivemqManager activemqManager;

    @Scheduled(fixedDelay = 60000, initialDelay = 1000)
    public void helloSend() {
        List<String> messageList = Lists.newArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String uuid = UUID.randomUUID().toString();
        messageList.add("[" + uuid + "][" + sdf.format(new Date()) + "] 消息发送1");
        messageList.add("[" + uuid + "][" + sdf.format(new Date()) + "] 消息发送2");
        messageList.add("[" + uuid + "][" + sdf.format(new Date()) + "] 消息发送3");
        activemqManager.sendQueueMessage(ActivemqConstant.MODE_QUEUE_NAME, messageList, new ActivemqCallback() {
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
