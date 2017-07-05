package com.rd.support.rabbitmq.core;

import lombok.Data;

/**
 * Created by BG297869 on 2017/7/3.
 */
@Data
public class RabbitmqClient {
    public static final String DEFAULT_HOST = "127.0.0.1";
    public static final String DEFAULT_USERNAME = "guest";
    public static final String DEFAULT_PASSWORD = "guest";
    public static final String DEFAULT_VIRTUAL_HOST = "/";
    public static final int DEFAULT_PORT = 5672;

    private String host = DEFAULT_HOST;
    private String username = DEFAULT_USERNAME;
    private String password = DEFAULT_PASSWORD;
    private String virtualHost = DEFAULT_VIRTUAL_HOST;
    private int port = DEFAULT_PORT;
}
