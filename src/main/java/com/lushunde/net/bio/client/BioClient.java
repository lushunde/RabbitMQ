package com.lushunde.net.bio.client;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.net.Socket;

/**
 * @ClassName BioClientUtil
 * @Description TODO 快来记录点什么吧，不要太懒了
 * @Author bellus
 * @Date 2022/6/17 17:13
 * @Version 1.0.0
 **/

public class BioClient {

    private int port ;
    private String host;
    private Socket socket;
    private OutputStream outputStream ;
    private InputStream inputStream ;
    private PrintWriter out ;
    private BufferedReader in ;


    public BioClient(@NotNull String host, @NotNull int port) {
        this.port = port;
        this.host = host;
    }

    public void open(){

        try {
            this.socket = new Socket(host, port);
            this.outputStream = this.socket.getOutputStream();
            this.inputStream = this.socket.getInputStream();
            this.out = new PrintWriter(this.outputStream, true);
            this.in = new BufferedReader(new InputStreamReader(this.inputStream));

            System.out.println("打开连接成功");
        }catch (Exception e){
            System.out.println("打开连接失败");
            e.printStackTrace();
        }

    }

    public void close(){

        try {
            if(out != null){
                out.close();
            }
        }catch (Exception e){
            System.out.println("关闭PrintWriter失败");
            e.printStackTrace();
        }

        try {
            if(in != null){
                in.close();
            }
        }catch (Exception e){
            System.out.println("关闭BufferedReader失败");
            e.printStackTrace();
        }

        try {
            if(inputStream != null){
                inputStream.close();
            }
        }catch (Exception e){
            System.out.println("关闭inputStream失败");
            e.printStackTrace();
        }
        try {
            if(outputStream != null){
                outputStream.close();
            }
        }catch (Exception e){
            System.out.println("关闭outputStream失败");
            e.printStackTrace();
        }

        try {
            if(socket != null){
                socket.close();
            }
        }catch (Exception e){
            System.out.println("关闭socket失败");
            e.printStackTrace();
        }
        System.out.println("关闭链接成功");
    }


    /**
     * @Description 发送一次 message
     * @Author Bellus
     * @Date 2022/6/17 17:15
     * @Version 1.0.0
     * @Param []
     * @return
     **/
    public void sendMessage(String message){
        checkHasMessage(message);

        checkHasOpen();
        checkHasOutPutStream();


        try {
            out.println(message);
            System.out.println("发送消息成功 message  = " + message);
        }catch (Exception e){
            System.out.println("发送消息失败 message  = " + message);
            e.printStackTrace();
        }


    }

    /**
     * @Description 发送 一条消息 并返回一条数据
     * @Author Bellus
     * @Date 2022/6/17 17:15
     * @Version 1.0.0
     * @Param []
     * @return
     **/
    public String sendMessageAndReadResult(String message){
        checkHasMessage(message);
        checkHasOpen();

        checkHasOutPutStream();
        checkHasInPutStream();

        try {
            this.out.println(message);
            System.out.println("发送消息成功 message  = " + message);
            String result = this.in.readLine();
            System.out.println("接受消息成功 result  = " + result);
            return result;
        }catch (Exception e) {
            System.out.println("接受消息失败");
            e.printStackTrace();
        }
        return null;

    }

    private void checkHasMessage(String message) {
        if (message == null) {
            throw new RuntimeException("发送消息内容不能为空");
        }
    }


    /**
     * @Description 检查是否已经建立socket链接
     * @Author Bellus
     * @Date 2022/6/17 17:39
     * @Version 1.0.0
     * @Param []
     * @return void
     **/
    private void checkHasOpen() {
        if (this.socket == null) {
            throw new RuntimeException("没有链接到服务，请使用  .open() ");
        }
    }

    private void checkHasOutPutStream() {
        if (this.outputStream == null) {
            throw new RuntimeException("outputStream 已经被关闭 ");
        }
    }

    private void checkHasInPutStream() {
        System.out.println(inputStream);
        if (this.inputStream == null) {
            throw new RuntimeException("inputStream 已经被关闭 ");
        }
    }








}
