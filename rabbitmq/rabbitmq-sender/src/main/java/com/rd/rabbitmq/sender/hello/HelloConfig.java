package com.rd.rabbitmq.sender.hello;

import com.rd.support.rabbitmq.manager.RabbitmqManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by BG297869 on 2017/7/3.
 */

@Configuration
@Slf4j
public class HelloConfig {

    @Bean(initMethod = "init")
    @ConfigurationProperties(prefix = "my.rabbitmq.hello")
    public RabbitmqManager helloSender() {
        log.info("初始化测试");
        return new HelloSender();
    }
}
