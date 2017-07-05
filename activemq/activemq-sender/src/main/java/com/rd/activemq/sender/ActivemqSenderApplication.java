package com.rd.activemq.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rd.support.activemq", "com.rd.activemq.sender.topicmode"})
public class ActivemqSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivemqSenderApplication.class, args);
    }
}
