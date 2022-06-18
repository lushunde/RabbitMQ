package com.lushunde.net.netty.client;

import com.lushunde.net.netty.servce.NettyServer;

/**
 * @ClassName ClientTest
 * @Description TODO 快来记录点什么吧，不要太懒了
 * @Author bellus
 * @Date 2022/6/18 20:14
 * @Version 1.0.0
 **/

public class ClientTest {

    public static void main(String[] args) throws Exception {

       new NettyClient("127.0.0.1", 8000).start();

    }

}
