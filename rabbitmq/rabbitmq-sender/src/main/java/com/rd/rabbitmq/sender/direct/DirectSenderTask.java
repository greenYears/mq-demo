package com.rd.rabbitmq.sender.direct;

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
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by BG297869 on 2017/7/3.
 */
@Component
@Slf4j
@EnableScheduling
public class DirectSenderTask {
    @Autowired
    private RabbitmqManager rabbitmqManager;

    @Scheduled(fixedDelay = 10000, initialDelay = 1000)
    public void sendHandler() throws IOException, TimeoutException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sdf.format(new Date());
        String uuid = UUID.randomUUID().toString();

        String message = "[" + uuid + "][" + now + "]" + "Direct Mode!";
        rabbitmqManager.sendDirectMessage(message, new RabbitmqCallback() {
            @Override
            public void sendCallback(String message) {
                log.info("[x - 1] 发送的消息是 {}", message);
            }

            @Override
            public void receiveCallback(String message) {

            }
        });
    }
}
