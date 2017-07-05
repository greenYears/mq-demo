package com.rd.rabbitmq.sender.headers;

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
    public RabbitmqManager headersSender() {
        return new HeadersSender();
    }

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "my.rabbitmq.headers2")
    public RabbitmqManager headers2Sender() {
        return new HeadersSender();
    }
}
