package com.rd.rabbitmq.receiver;

import com.rd.rabbitmq.receiver.direct.DirectReceiver;
import com.rd.rabbitmq.receiver.fanout.FanoutReceiver;
import com.rd.rabbitmq.receiver.headers.HeadersReceiver;
import com.rd.rabbitmq.receiver.hello.HelloReceiver;
import com.rd.rabbitmq.receiver.topic.TopicReceiver;
import com.rd.support.rabbitmq.core.RabbitmqCallback;
import com.rd.support.rabbitmq.manager.RabbitmqManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rd.rabbitmq.receiver.headers"})
@Slf4j
public class RabbitmqReceiverApplication {

	public static void main(String[] args) throws IOException, TimeoutException {
		ApplicationContext applicationContext = SpringApplication.run(RabbitmqReceiverApplication.class, args);
		receiveHeadersMessage(applicationContext);
	}

	/**
	 * hello
	 * @param ctx
	 * @throws IOException
	 * @throws TimeoutException
	 */
	private static void receiveHelloMessage(ApplicationContext ctx) throws IOException, TimeoutException {
		RabbitmqManager receiver = ctx.getBean(HelloReceiver.class);
		receiver.receiveDirectMessage(new RabbitmqCallback() {
			@Override
			public void sendCallback(String message) {
				// TODO: 2017/7/3
			}

			@Override
			public void receiveCallback(String message) {
				log.info("[x - 1] 接收到的消息是 {}", message);
			}
		});
	}

	/**
	 * direct
	 * @param ctx
	 * @throws IOException
	 * @throws TimeoutException
	 */
	private static void receiveDirectMessage(ApplicationContext ctx) throws IOException, TimeoutException {
		RabbitmqManager receiver = ctx.getBean(DirectReceiver.class);
		receiver.receiveDirectMessage(new RabbitmqCallback() {
			@Override
			public void sendCallback(String message) {
				// TODO: 2017/7/3
			}

			@Override
			public void receiveCallback(String message) {
				log.info("[x - 1] 接收到的消息是 {}", message);
			}
		});
	}

	/**
	 * topic
	 * @param ctx
	 * @throws IOException
	 * @throws TimeoutException
	 */
	private static void receiveTopicMessage(ApplicationContext ctx) throws IOException, TimeoutException {
		RabbitmqManager topicReceiver = ctx.getBean("topicReceiver", TopicReceiver.class);
		topicReceiver.receiveTopicMessage(new RabbitmqCallback() {
			@Override
			public void sendCallback(String message) {
				// TODO: 2017/7/3
			}

			@Override
			public void receiveCallback(String message) {
				log.info("[x - 1] 接收到的消息是 {}", message);
			}
		});

		RabbitmqManager topic2Receiver = ctx.getBean("topic2Receiver", TopicReceiver.class);
		topic2Receiver.receiveTopicMessage(new RabbitmqCallback() {
			@Override
			public void sendCallback(String message) {
				// TODO: 2017/7/3
			}

			@Override
			public void receiveCallback(String message) {
				log.info("[x - 2] 接收到的消息是 {}", message);
			}
		});
	}

	/**
	 * fanout
	 *
	 * @param ctx
	 * @throws IOException
	 * @throws TimeoutException
	 */
	private static void receiveFanoutMessage(ApplicationContext ctx) throws IOException, TimeoutException {
		RabbitmqManager fanoutReceiver = ctx.getBean("fanoutReceiver", FanoutReceiver.class);
		fanoutReceiver.receiveFanoutMessage(new RabbitmqCallback() {
			@Override
			public void sendCallback(String message) {
				// TODO: 2017/7/3
			}

			@Override
			public void receiveCallback(String message) {
				log.info("[x - 1] 接收到的消息是 {}", message);
			}
		});

		RabbitmqManager fanout2Receiver = ctx.getBean("fanout2Receiver", FanoutReceiver.class);
		fanout2Receiver.receiveFanoutMessage(new RabbitmqCallback() {
			@Override
			public void sendCallback(String message) {
				// TODO: 2017/7/3
			}

			@Override
			public void receiveCallback(String message) {
				log.info("[x - 2] 接收到的消息是 {}", message);
			}
		});
	}

	/**
	 * headers
	 *
	 * @param ctx
	 * @throws IOException
	 * @throws TimeoutException
	 */
	private static void receiveHeadersMessage(ApplicationContext ctx) throws IOException, TimeoutException {
		RabbitmqManager headersReceiver = ctx.getBean("headersReceiver", HeadersReceiver.class);
		String xMatch = (String) headersReceiver.getHeadersProperties().get("x-match");
		log.info("[x - 1] xMatch = {}", xMatch);
		headersReceiver.receiveHeadersMessage(new RabbitmqCallback() {
			@Override
			public void sendCallback(String message) {
				// TODO: 2017/7/3
			}

			@Override
			public void receiveCallback(String message) {
				log.info("[x - 1] 接收到的消息是 {}", message);
			}
		});

		RabbitmqManager headers2Receiver = ctx.getBean("headers2Receiver", HeadersReceiver.class);
		String x2Match = (String) headers2Receiver.getHeadersProperties().get("x-match");
		log.info("[x - 2] xMatch = {}", x2Match);
		headers2Receiver.receiveHeadersMessage(new RabbitmqCallback() {
			@Override
			public void sendCallback(String message) {
				// TODO: 2017/7/3
			}

			@Override
			public void receiveCallback(String message) {
				log.info("[x - 2] 接收到的消息是 {}", message);
			}
		});
	}
}
