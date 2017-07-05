package com.rd.rabbitmq.sender.direct;

import com.rd.rabbitmq.sender.hello.HelloSender;
import com.rd.support.rabbitmq.manager.RabbitmqManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by BG297869 on 2017/7/3.
 */

@Configuration
public class DirectConfig {

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "my.rabbitmq.direct")
    public RabbitmqManager directSender() {
        return new DirectSender();
    }
}
