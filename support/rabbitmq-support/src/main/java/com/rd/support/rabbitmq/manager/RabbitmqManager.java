package com.rd.support.rabbitmq.manager;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rd.support.rabbitmq.core.RabbitmqCallback;
import com.rd.support.rabbitmq.core.RabbitmqClient;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by BG297869 on 2017/6/26.
 */
@Getter
@Setter
@Slf4j
public abstract class RabbitmqManager {
    public static final String DIRECT_TYPE = "direct"; // 默认模式
    public static final String FANOUT_TYPE = "fanout"; // 广播
    public static final String TOPIC_TYPE = "topic"; // 主题
    public static final String HEADERS_TYPE = "headers"; // 头

    protected RabbitmqClient client;
    protected String endpointName;
    protected String routingKey;
    protected String exchangeName = "";
    protected Map<String, Object> headersProperties;
    //    protected  Connection connection;
//    protected  Channel channel;
    ConnectionFactory factory;

    public void init() throws IOException, TimeoutException {
        log.info("************** init[begin] **************");
        factory = new ConnectionFactory();
        factory.setHost(client.getHost());
        factory.setUsername(client.getUsername());
        factory.setPassword(client.getPassword());
        factory.setPort(client.getPort());
        factory.setVirtualHost(client.getVirtualHost());

        if (routingKey == null || "".equals(routingKey.trim())) {
            routingKey = endpointName;
        }

        log.info("\t\thost: {}", client.getHost());
        log.info("\t\tusername: {}", client.getUsername());
        log.info("\t\tpassword: {}", client.getPassword());
        log.info("\t\tport: {}", client.getPort());
        log.info("\t\tvirtual-host: {}", client.getVirtualHost());
        log.info("\t\tendpoint-name: {}", endpointName);
        log.info("\t\texchange-name: {}", exchangeName);
        log.info("\t\trouting-key: {}", routingKey);
        if (this.headersProperties != null) {
            log.info("\t\theaders:");
            this.headersProperties.entrySet().forEach(target -> {
                log.info("\t\t\t {} -> {}", target.getKey(), target.getValue());
            });
        }
        log.info("************** init[end] **************");
    }

    /**
     * 发送消息【direct】
     *
     * @param message
     * @param callback
     * @throws IOException
     * @throws TimeoutException
     */
    public void sendDirectMessage(String message, RabbitmqCallback callback) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        if (exchangeName == null || "".equals(exchangeName.trim())) { // 如果交换机名称是空的，则使用rabbitmq默认的direct交换机，不需要将队列和交换机进行绑定操作
            exchangeName = "";
            // durable 是否持久化 exclusive 是否私有 autoDelete 当最后一个消费者取消订阅的时候，队列是否自动移除
            channel.queueDeclare(endpointName, false, false, false, null);
        } else {
            channel.exchangeDeclare(exchangeName, DIRECT_TYPE);
            channel.queueDeclare(endpointName, false, false, false, null);
            channel.queueBind(endpointName, exchangeName, routingKey); // 默认使用队列名称为routingKey
        }
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes("UTF-8"));
        sendMessageHandler(channel, message, callback);
        channel.close();
        connection.close();
    }

    /**
     * 发送消息【topic】
     *
     * @param message
     * @param callback
     * @throws IOException
     * @throws TimeoutException
     */
    public void sendTopicMessage(String routingKey, String message, RabbitmqCallback callback) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, TOPIC_TYPE);
        channel.queueDeclare(endpointName, false, false, false, null);
        channel.queueBind(endpointName, exchangeName, this.routingKey); // 默认使用队列名称为routingKey

        channel.basicPublish(exchangeName, routingKey, null, message.getBytes("UTF-8"));
        sendMessageHandler(channel, message, callback);
        channel.close();
        connection.close();
    }

    /**
     * 发送消息【fanout】
     *
     * @param message
     * @param callback
     * @throws IOException
     * @throws TimeoutException
     */
    public void sendFanoutMessage(String message, RabbitmqCallback callback) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, FANOUT_TYPE);
        channel.queueDeclare(endpointName, false, false, false, null);
        channel.queueBind(endpointName, exchangeName, this.routingKey); // 默认使用队列名称为routingKey

        channel.basicPublish(exchangeName, "", null, message.getBytes("UTF-8"));  // 广播形式无需routingKey
        sendMessageHandler(channel, message, callback);
        channel.close();
        connection.close();
    }

    /**
     * 发送消息【headers】
     *
     * @param message
     * @param callback
     * @throws IOException
     * @throws TimeoutException
     */
    public void sendHeadersMessage(String message, RabbitmqCallback callback) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, HEADERS_TYPE);
        channel.queueDeclare(endpointName, false, false, false, null);
        channel.queueBind(endpointName, exchangeName, "", this.headersProperties); // 默认使用队列名称为routingKey

        // 设置头部参数
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.headers(this.headersProperties);

        channel.basicPublish(exchangeName, "", builder.build(), message.getBytes("UTF-8"));  // 广播形式无需routingKey
        sendMessageHandler(channel, message, callback);
        channel.close();
        connection.close();
    }

    /**
     * 接收消息【direct】
     *
     * @param callback
     * @throws IOException
     * @throws TimeoutException
     */
    public void receiveDirectMessage(RabbitmqCallback callback) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        if (exchangeName == null || "".equals(exchangeName.trim())) {
            exchangeName = "";
            channel.queueDeclare(endpointName, false, false, false, null);
        } else {
            channel.exchangeDeclare(exchangeName, DIRECT_TYPE);
            channel.queueDeclare(endpointName, false, false, false, null);
            channel.queueBind(endpointName, exchangeName, routingKey);
        }
        receiveMessageHandler(channel, callback);
    }

    /**
     * 接收消息【topic】
     *
     * @param callback
     * @throws IOException
     * @throws TimeoutException
     */
    public void receiveTopicMessage(RabbitmqCallback callback) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, TOPIC_TYPE);
        channel.queueDeclare(endpointName, false, false, false, null);
        channel.queueBind(endpointName, exchangeName, routingKey);
        receiveMessageHandler(channel, callback);
    }

    /**
     * 接收消息【fanout】
     *
     * @param callback
     * @throws IOException
     * @throws TimeoutException
     */
    public void receiveFanoutMessage(RabbitmqCallback callback) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, FANOUT_TYPE);
        channel.queueDeclare(endpointName, false, false, false, null);
        channel.queueBind(endpointName, exchangeName, routingKey);
        receiveMessageHandler(channel, callback);
    }

    /**
     * 接收消息【headers】
     *
     * @param callback
     * @throws IOException
     * @throws TimeoutException
     */
    public void receiveHeadersMessage(RabbitmqCallback callback) throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, HEADERS_TYPE);
        channel.queueDeclare(endpointName, false, false, false, null);
        channel.queueBind(endpointName, exchangeName, "", this.headersProperties);
        receiveMessageHandler(channel, callback);
    }

    /**
     * 发送消息
     */
    abstract public void sendMessageHandler(Channel channel, String message, RabbitmqCallback callback) throws IOException;

    /**
     * 接收消息
     *
     * @param channel
     * @param callback
     * @throws IOException
     */
    abstract public void receiveMessageHandler(Channel channel, RabbitmqCallback callback) throws IOException;
}
