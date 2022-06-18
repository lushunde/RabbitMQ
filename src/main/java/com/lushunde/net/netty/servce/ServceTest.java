package com.lushunde.net.netty.servce;

/**
 * @ClassName ServceTest
 * @Description TODO 快来记录点什么吧，不要太懒了
 * @Author bellus
 * @Date 2022/6/18 20:06
 * @Version 1.0.0
 **/

public class ServceTest {

    public static void main(String[] args) {
        new NettyServer(8000).start();
    }
}
