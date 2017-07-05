package com.rd.rabbitmq.sender.fanout;

import com.rd.support.rabbitmq.constants.RabbitmqConstant;
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
public class FanoutSenderTask {
    @Autowired
    private RabbitmqManager fanoutSender;
    @Autowired
    private RabbitmqManager fanout2Sender;

    @Scheduled(fixedDelay = 20000, initialDelay = 1000)
    public void sendHandler() throws IOException, TimeoutException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        String uuid = UUID.randomUUID().toString();

        String message = "[1][" + uuid + "][" + now + "]" + "Fanout Mode!";
        fanoutSender.sendFanoutMessage(message, new RabbitmqCallback() {
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

    @Scheduled(fixedDelay = 75000, initialDelay = 1000)
    public void send2Handler() throws IOException, TimeoutException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        String uuid = UUID.randomUUID().toString();

        String message = "[2][" + uuid + "][" + now + "]" + "Fanout Mode!";
        fanout2Sender.sendFanoutMessage(message, new RabbitmqCallback() {
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
