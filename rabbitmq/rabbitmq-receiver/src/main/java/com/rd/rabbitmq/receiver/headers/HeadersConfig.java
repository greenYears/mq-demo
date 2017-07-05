package com.rd.rabbitmq.receiver.headers;

import com.rd.support.rabbitmq.manager.RabbitmqManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by BG297869 on 2017/7/3.
 */

@Configuration
public class HeadersConfig {

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "my.rabbitmq.headers")
    public RabbitmqManager headersReceiver() {
        return new HeadersReceiver();
    }

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "my.rabbitmq.headers2")
    public RabbitmqManager headers2Receiver() {
        return new HeadersReceiver();
    }
}
