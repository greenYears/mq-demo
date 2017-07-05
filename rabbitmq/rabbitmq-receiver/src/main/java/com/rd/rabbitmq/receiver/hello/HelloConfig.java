package com.rd.rabbitmq.receiver.hello;

import com.rd.support.rabbitmq.manager.RabbitmqManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by BG297869 on 2017/7/3.
 */

@Configuration
public class HelloConfig {

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "my.rabbitmq.hello")
    public RabbitmqManager helloReceiver() {
        return new HelloReceiver();
    }
}
