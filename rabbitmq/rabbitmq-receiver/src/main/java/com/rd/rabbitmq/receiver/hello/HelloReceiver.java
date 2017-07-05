package com.rd.rabbitmq.receiver.hello;

import com.rabbitmq.client.*;
import com.rd.support.rabbitmq.constants.RabbitmqConstant;
import com.rd.support.rabbitmq.core.RabbitmqCallback;
import com.rd.support.rabbitmq.manager.RabbitmqManager;

import java.io.IOException;

/**
 * Created by BG297869 on 2017/7/3.
 */
public class HelloReceiver extends RabbitmqManager {

    @Override
    public void sendMessageHandler(Channel channel, String message, RabbitmqCallback callback) throws IOException {
        // TODO: 2017/7/3 producer sender message handler

    }

    @Override
    public void receiveMessageHandler(Channel channel, RabbitmqCallback callback) throws IOException {
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                if (callback != null) {
                    callback.receiveCallback(message);
                }
            }
        };

        channel.basicConsume(this.getEndpointName(), Boolean.TRUE, consumer);
    }
}
