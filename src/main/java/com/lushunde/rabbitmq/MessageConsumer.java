package com.lushunde.rabbitmq;


import com.rabbitmq.client.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

/**
 * @Description RabbitMQ的api演示 消费队列
 * @Author Bellus
 * @Date 2022/5/29 12:30
 * @Version 1.0.0
 **/
public class MessageConsumer {

    private final static String QUEUE_NAME = "ORIGIN_QUEUE";

    public static void main(String[] args) throws IOException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, TimeoutException {
    //读取配置文件
        ResourceBundle bundle = ResourceBundle.getBundle("rabbitmq");
        String url = bundle.getString("rabbitmq.uri");

        System.out.println("url = " + url);
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri(url);

        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" Waiting for message....");

        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) {
                String msg = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received message : '" + msg + "' " );
                System.out.println("messageId : " + properties.getMessageId() );
                System.out.println(properties.getHeaders().get("name") + " -- " + properties.getHeaders().get("level"));
            }
        };

        // 开始获取消息
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

}
