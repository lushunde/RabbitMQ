package com.lushunde.net.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @ClassName NioClientTest
 * @Description TODO 快来记录点什么吧，不要太懒了
 * @Author bellus
 * @Date 2022/6/18 15:40
 * @Version 1.0.0
 **/

public class NioClientTest {

    public static void main(String[] args) throws IOException {

        NioClient client = new NioClient("127.0.0.1",8000);

        client.sendMessage("测试一");
        client.sendMessage("测试二");

        client.close();


    }


}
