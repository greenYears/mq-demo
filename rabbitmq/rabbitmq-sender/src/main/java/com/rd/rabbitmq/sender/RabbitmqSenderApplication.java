package com.rd.rabbitmq.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rd.rabbitmq.sender.headers"})
public class RabbitmqSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqSenderApplication.class, args);
	}
}
