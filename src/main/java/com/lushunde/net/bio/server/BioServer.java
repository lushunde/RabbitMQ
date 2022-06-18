package com.lushunde.net.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName BioServer
 * @Description TODO 快来记录点什么吧，不要太懒了
 * @Author bellus
 * @Date 2022/6/17 16:45
 * @Version 1.0.0
 **/


public class BioServer {

        private int port ;

        private ServerSocket server ;

    public BioServer(int port) {
        this.port = port;

        try {
            this.server = new ServerSocket(port);
        }catch (IOException e) {
            System.out.println("初始化ServerSocket失败" );
            e.printStackTrace();
        }

    }


    /**
     * @Description 开始监听
     * @Author Bellus
     * @Date 2022/6/17 19:28
     * @Version 1.0.0
     * @Param [timeServerHandlerClass]
     * @return void
     **/
    public void start() {

        System.out.println("启动ServerSocket开始，端口="+ port );

        while (true){
            Socket socket = null;
            try {
                socket = this.server.accept();
            }catch (Exception e) {
                System.out.println("启动ServerSocket监听失败" );
                e.printStackTrace();
            }

            try {
                new Thread(new TimeServerHandler(socket)).start();
            }catch (Exception e) {
                System.out.println("异步执行任务失败" );
                e.printStackTrace();
            }

        }

    }
}
