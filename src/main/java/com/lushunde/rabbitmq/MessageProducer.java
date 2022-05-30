package com.lushunde.rabbitmq;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * @Description RabbitMQ的api演示 消费队列
 * @Author Bellus
 * @Date 2022/5/29 12:31
 * @Version 1.0.0
 **/
public class MessageProducer {

    private final static String QUEUE_NAME = "ORIGIN_QUEUE";

    public static void main(String[] args) throws Exception {
        //读取配置文件
        ResourceBundle bundle = ResourceBundle.getBundle("rabbitmq");
        String url = bundle.getString("rabbitmq.uri");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(url);

        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("name", "gupao");
        headers.put("level", "top");

        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)   // 2代表持久化
                .contentEncoding("UTF-8")  // 编码
                .expiration("10000")  // TTL，过期时间
                .headers(headers) // 自定义属性
                .priority(5) // 优先级，默认为5，配合队列的 x-max-priority 属性使用
                .messageId(String.valueOf(UUID.randomUUID()))
                .build();

        String msg = "Hello world, Rabbit MQ";
        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 发送消息
        // String exchange, String routingKey, BasicProperties props, byte[] body
        channel.basicPublish("", QUEUE_NAME, properties, msg.getBytes());

        channel.close();
        conn.close();
    }


}
