package com.rd.rabbitmq.sender.fanout;

import com.rd.support.rabbitmq.manager.RabbitmqManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by BG297869 on 2017/7/3.
 */

@Configuration
public class FanoutConfig {

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "my.rabbitmq.fanout")
    public RabbitmqManager fanoutSender() {
        return new FanoutSender();
    }

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "my.rabbitmq.fanout2")
    public RabbitmqManager fanout2Sender() {
        return new FanoutSender();
    }
}
