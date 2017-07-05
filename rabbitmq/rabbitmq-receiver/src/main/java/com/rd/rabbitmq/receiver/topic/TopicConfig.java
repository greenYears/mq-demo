package com.rd.rabbitmq.receiver.topic;

import com.rd.support.rabbitmq.manager.RabbitmqManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by BG297869 on 2017/7/3.
 */

@Configuration
public class TopicConfig {

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "my.rabbitmq.topic")
    public RabbitmqManager topicReceiver() {
        return new TopicReceiver();
    }

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "my.rabbitmq.topic2")
    public RabbitmqManager topic2Receiver() {
        return new TopicReceiver();
    }
}
