package com.lushunde.net.bio.client;

/**
 * @ClassName BioClientTest
 * @Description TODO 快来记录点什么吧，不要太懒了
 * @Author bellus
 * @Date 2022/6/17 17:42
 * @Version 1.0.0
 **/

public class BioClientTest {

    public static void main(String[] args) {
        BioClient client = new BioClient("localhost", 8080);

        client.open();

        client.sendMessage("测试第一句");

        client.sendMessage("测试第二句");

        String s = client.sendMessageAndReadResult("测试第三局");

        System.out.println(s);
        String s2 = client.sendMessageAndReadResult("测试第四局");

        System.out.println(s2);

        client.close();


    }


}
