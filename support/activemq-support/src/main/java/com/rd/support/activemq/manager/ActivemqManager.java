package com.rd.support.activemq.manager;

import com.rd.support.activemq.core.ActivemqCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.*;
import java.util.List;

/**
 * Created by BG297869 on 2017/7/3.
 */
@Component
@Slf4j
public class ActivemqManager {
    @Value("${my.activemq.username}")
    private String username = ActiveMQConnection.DEFAULT_USER;
    @Value("${my.activemq.password}")
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;
    @Value("${my.activemq.brokerURL}")
    private String brokerURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    private ConnectionFactory factory; // 链接工厂


    @PostConstruct
    public void init() {
        log.info("********************* 开始进行初始化操作 *********************");
        log.info("\t\tusername = {}", username);
        log.info("\t\tpassword = {}", password);
        log.info("\t\tbrokerURL = {}", brokerURL);
        factory = new ActiveMQConnectionFactory(username, password, brokerURL);
    }

    /**
     * 发送消息
     *
     * @param queueName
     * @param messageList
     */
    public void sendQueueMessage(String queueName, List<String> messageList, ActivemqCallback callback) {
        Connection conn; // JMS 客户端到JMS Provider 的连接
        Session session; // 一个发送或接收消息的线程
        Destination destination; // 消息目的地，消息发送给谁
        MessageProducer producer; // 消息生产者

        try {
            conn = factory.createConnection();
            conn.start();
            session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue(queueName);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT); // 此处学习，不进行持久化

            messageList.stream().forEach(message -> {
                Message msg;
                try {
                    msg = session.createTextMessage(message);
                    producer.send(msg);
                    if (callback != null) {
                        callback.sendCallback(msg);
                    }
                } catch (JMSException e) {
                    log.error("send message handler error", e);
                }
            });
            session.commit();

            conn.close();

        } catch (JMSException e) {
            log.error("sendMessage error", e);
        } finally {

        }
    }

    /**
     * 发送主题模式的消息
     *
     * @param topicName
     * @param msgList
     * @param callback
     */
    public void sendTopicMessage(String topicName, List<String> msgList, ActivemqCallback callback) {
        Connection conn; // JMS 客户端到JMS Provider 的连接
        Session session; // 一个发送或接收消息的线程
        Destination destination; // 消息目的地，消息发送给谁
        MessageProducer producer; // 消息生产者

        try {
            conn = factory.createConnection();
            conn.start();

            session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            destination = session.createTopic(topicName);
            producer = session.createProducer(destination);

            msgList.stream().forEach(msg -> {
                try {
                    TextMessage message = session.createTextMessage(msg);
                    producer.send(message);
                    if (callback != null) {
                        callback.sendCallback(message);
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });

            session.commit();
            conn.close();

        } catch (JMSException e) {
            log.error("sendTopicMessage error", e);
        }
    }

    /**
     * 接收消息
     *
     * @param queueName
     * @param callback
     */
    public void receiverQueueMessage(String queueName, ActivemqCallback callback) {
        Connection conn = null; // JMS 客户端到JMS Provider 的连接
        Destination destination; // 消息目的地，消息发送给谁
        MessageConsumer consumer = null; // 消费者
        try {
            log.info("receiverMessageListener 监听消息");
            conn = factory.createConnection();
            conn.start();
            final Session session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue(queueName);
            consumer = session.createConsumer(destination);

            consumer.setMessageListener(message -> {
                try {
                    if (callback != null) {
                        callback.receiveCallback(message);
                    }
                    session.commit();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });

        } catch (JMSException e) {
            log.error("receiverMessageListener error", e);
        } finally {
//            this.close(conn);
        }
    }

    /**
     * 接收消息
     *
     * @param topicName
     * @param callback
     */
    public void receiveTopicMessage(String topicName, ActivemqCallback callback) {
        Connection conn = null; // JMS 客户端到JMS Provider 的连接
        Destination destination; // 消息目的地，消息发送给谁
        MessageConsumer consumer = null; // 消费者
        try {
            log.info("receiverMessageListener 监听消息");
            conn = factory.createConnection();
            conn.start();
            final Session session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            destination = session.createTopic(topicName);
            consumer = session.createConsumer(destination);

            consumer.setMessageListener(message -> {
                try {
                    if (callback != null) {
                        callback.receiveCallback(message);
                    }
                    session.commit();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });

        } catch (JMSException e) {
            log.error("receiveTopicMessage error", e);
        } finally {
//            this.close(conn);
        }
    }

}
