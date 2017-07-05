package com.rd.rabbitmq.receiver.direct;

import com.rd.rabbitmq.receiver.hello.HelloReceiver;
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
    public RabbitmqManager directReceiver() {
        return new DirectReceiver();
    }
}
