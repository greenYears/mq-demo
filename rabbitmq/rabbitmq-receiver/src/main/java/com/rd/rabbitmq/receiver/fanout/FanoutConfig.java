package com.rd.rabbitmq.receiver.fanout;

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
    public RabbitmqManager fanoutReceiver() {
        return new FanoutReceiver();
    }

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "my.rabbitmq.fanout2")
    public RabbitmqManager fanout2Receiver() {
        return new FanoutReceiver();
    }
}
