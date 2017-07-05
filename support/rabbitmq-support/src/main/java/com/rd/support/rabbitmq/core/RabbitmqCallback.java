package com.rd.support.rabbitmq.core;

/**
 * Created by BG297869 on 2017/7/3.
 */
public interface RabbitmqCallback {

    void sendCallback(String message);

    void receiveCallback(String message);
}
