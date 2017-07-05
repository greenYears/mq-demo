package com.rd.activemq.sender.topicmode;

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
import java.util.Random;
import java.util.UUID;

/**
 * Created by BG297869 on 2017/7/3.
 */
@Component
@EnableScheduling
@Slf4j
public class TopicSender {

    @Autowired
    private ActivemqManager activemqManager;

    @Scheduled(fixedDelay = 10000, initialDelay = 1000)
    public void sendMessage() {
        List<String> messageList = Lists.newArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String uuid = UUID.randomUUID().toString();
        messageList.add("[" + uuid + "][" + sdf.format(new Date()) + "] 消息发送");
        activemqManager.sendTopicMessage(ActivemqConstant.MODE_TOPIC_NAME, messageList, new ActivemqCallback() {
            @Override
            public void receiveCallback(Message message) throws JMSException {

            }

            @Override
            public void sendCallback(Message message) throws JMSException {
                log.info("[x - 1] 发送的消息是 {}", message);
            }
        });

        Random random = new Random();
        int sleepInt = random.nextInt(5000);
        try {
            log.info("[x - 1] 休眠{}ms", sleepInt);
            Thread.sleep(sleepInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
