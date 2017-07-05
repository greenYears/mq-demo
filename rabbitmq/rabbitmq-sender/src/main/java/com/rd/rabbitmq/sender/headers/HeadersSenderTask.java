package com.rd.rabbitmq.sender.headers;

import com.rd.support.rabbitmq.core.RabbitmqCallback;
import com.rd.support.rabbitmq.manager.RabbitmqManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by BG297869 on 2017/7/3.
 */
@Component
@Slf4j
@EnableScheduling
public class HeadersSenderTask {
    @Autowired
    private RabbitmqManager headersSender;
    @Autowired
    private RabbitmqManager headers2Sender;

    @Scheduled(fixedDelay = 20000, initialDelay = 1000)
    public void sendHandler() throws IOException, TimeoutException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        String uuid = UUID.randomUUID().toString();

        String xMatch = (String) headersSender.getHeadersProperties().get("x-match");
        String message = "[1][" + xMatch + "][" + uuid + "][" + now + "]" + "Headers Mode!";
        headersSender.sendHeadersMessage(message, new RabbitmqCallback() {
            @Override
            public void sendCallback(String message) {
                log.info("[x - 1] 发送的消息是 {}", message);
            }

            @Override
            public void receiveCallback(String message) {

            }
        });

        int sleepInt = new Random().nextInt(5000);
        try {
            log.info("[x - 1] 休眠 {}ms", sleepInt);
            Thread.sleep(sleepInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedDelay = 45000, initialDelay = 1000)
    public void send2Handler() throws IOException, TimeoutException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        String uuid = UUID.randomUUID().toString();
        String xMatch = (String) headers2Sender.getHeadersProperties().get("x-match");
        String message = "[2][" + xMatch + "][" + uuid + "][" + now + "]" + "Headers Mode!";
        headers2Sender.sendHeadersMessage(message, new RabbitmqCallback() {
            @Override
            public void sendCallback(String message) {
                log.info("[x - 2] 发送的消息是 {}", message);
            }

            @Override
            public void receiveCallback(String message) {

            }
        });

        int sleepInt = new Random().nextInt(5000);
        try {
            log.info("[x - 2] 休眠 {}ms", sleepInt);
            Thread.sleep(sleepInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
