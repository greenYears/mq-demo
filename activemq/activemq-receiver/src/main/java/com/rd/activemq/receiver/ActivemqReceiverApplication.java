package com.rd.activemq.receiver;

import com.rd.activemq.receiver.hello.HelloReceiver;
import com.rd.activemq.receiver.queuemode.QueueReceiver;
import com.rd.activemq.receiver.topicmode.TopicReceiver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rd.support.activemq", "com.rd.activemq.receiver.topicmode"})
public class ActivemqReceiverApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext =  SpringApplication.run(ActivemqReceiverApplication.class, args);

		TopicReceiver receiver = applicationContext.getBean(TopicReceiver.class);
		receiver.receiveMessage();
		receiver.receiveMessage2();

	}
}
