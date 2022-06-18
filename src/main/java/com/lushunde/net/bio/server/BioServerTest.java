package com.lushunde.net.bio.server;

/**
 * @ClassName BioServerTest
 * @Description TODO 快来记录点什么吧，不要太懒了
 * @Author bellus
 * @Date 2022/6/17 18:52
 * @Version 1.0.0
 **/

public class BioServerTest {

    public static void main(String[] args) {

        BioServer bioServer = new BioServer(8080);

        bioServer.start();

    }

}
