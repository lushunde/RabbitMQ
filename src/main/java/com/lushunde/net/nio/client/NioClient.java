package com.lushunde.net.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @ClassName BioClient
 * @Description TODO 快来记录点什么吧，不要太懒了
 * @Author bellus
 * @Date 2022/6/18 15:51
 * @Version 1.0.0
 **/

public class NioClient {

    private int port;
    private String host;

    private SocketChannel socketChannel;


    public void refash (){
        try {
            open(this.host,this.port);


        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("刷新socketChannel失败",e);
        }

    }

    public NioClient(String host, int port) {
        this.port = port;
        this.host = host;

        try {

            open(this.host, this.port);


        }catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("初始化socketChannel失败",e);
        }
    }

    private void open(String host, int port) throws IOException {
        // 创建 链接
        SocketChannel socketChannel1 = SocketChannel.open();

        //设置非阻塞
        socketChannel1.configureBlocking(false);
        System.out.println("设置非阻塞");

        // 设置服务器地址
        InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);

//        //判断连接
//        if(!socketChannel1.connect(inetSocketAddress)){
//            //链接失败
//            while (!socketChannel1.finishConnect()){
//                System.out.println("客户端非阻塞，干点其他的事情吧");
//            }
//        }


        // 链接 服务器
        boolean connect = socketChannel1.connect(inetSocketAddress);



        if(connect){
            // 处理逻辑
            System.out.println("客户端链接成功，地址："+ this.host+ "   端口:"+ this.port);
            this.socketChannel = socketChannel1;
        }else{
            while (!socketChannel1.finishConnect()){
                System.out.println("链接中...");
            }
            System.out.println("客户端链接成功，地址："+ this.host+ "   端口:"+ this.port);
            this.socketChannel = socketChannel1;

            //throw new RuntimeException("初始化socketChannel失败");
        }
    }


    /**
     * @Description 发送和接受消息
     * @Author Bellus
     * @Date 2022/6/18 16:21
     * @Version 1.0.0
     * @Param [Message]
     * @return com.lushunde.net.nio.client.BioClient
     **/
    public NioClient sendMessage(String message) {
        checkHasSocketChannel(this.socketChannel);

        //根据字节数组大小创建一个buffer大小
        ByteBuffer byteBuffer= ByteBuffer.wrap(message.getBytes());

        try {
            //将buffer中的数据写入到channel中
            socketChannel.write(byteBuffer);

            System.out.println("发送成功message = " + message);
        }catch (IOException e) {
            throw new RuntimeException("发送失败message = " + message,e);
        }

        return this;
    }

    /**
     * @Description 发送和接受消息
     * @Author Bellus
     * @Date 2022/6/18 16:21
     * @Version 1.0.0
     * @Param [Message]
     * @return com.lushunde.net.nio.client.BioClient
     **/
    public String readMessage() {
        checkHasSocketChannel(this.socketChannel);

        //根据字节数组大小创建一个buffer大小
        ByteBuffer buf = ByteBuffer.allocate(1024);

        try {

            int bytesRead = socketChannel.read(buf);

            if(bytesRead>0){
                String s = new String(buf.array(), 0, bytesRead);
                System.out.println("读取消息   s = " + s);
                return s;
            }else{
                System.out.println("读取消息空");

            }


        }catch (IOException e) {
            throw new RuntimeException("读取消息失败message " ,e);
        }

        return null;
    }



    public void close(){
        try {
            socketChannel.socket().close();
            socketChannel.close();


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端关闭链接失败");
            throw new RuntimeException("客户端关闭链接失败");
        }
    }


    private void checkHasSocketChannel(SocketChannel socketChannel) {
        if (this.socketChannel == null) {
            throw new RuntimeException("没有链接到服务，请使用 .refrsh() ");
        }
    }


}
