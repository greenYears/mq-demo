package com.rd.support.activemq.core;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Created by BG297869 on 2017/7/3.
 */
public interface ActivemqCallback {

    void receiveCallback(Message message) throws JMSException;
    void sendCallback(Message message) throws JMSException;
}
