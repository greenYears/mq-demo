package com.rd.rabbitmq.sender.fanout;

import com.rabbitmq.client.Channel;
import com.rd.support.rabbitmq.core.RabbitmqCallback;
import com.rd.support.rabbitmq.manager.RabbitmqManager;

import java.io.IOException;

/**
 * Created by BG297869 on 2017/7/3.
 */
public class FanoutSender extends RabbitmqManager {

    @Override
    public void sendMessageHandler(Channel channel, String message, RabbitmqCallback callback) throws IOException {

        if (callback != null) {
            callback.sendCallback(message);
        }

    }

    @Override
    public void receiveMessageHandler(Channel channel, RabbitmqCallback callback) throws IOException {
        // TODO: 2017/7/3 consumer receiver message handler
    }
}
