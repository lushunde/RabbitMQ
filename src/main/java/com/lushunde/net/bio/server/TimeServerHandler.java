package com.lushunde.net.bio.server;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * @ClassName TimeServerHandler
 * @Description TODO 快来记录点什么吧，不要太懒了
 * @Author bellus
 * @Date 2022/6/17 16:52
 * @Version 1.0.0
 **/

public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket){
        this.socket = socket;
    }


    @Override
    public void run() {


        try ( Socket socket  = this.socket;
              BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
              PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true)){


            String body = null;

            while ((body = in.readLine())!=null && body.length()!=0){
                System.out.println("the time server receive msg :" + body);
                out.println(new Date().toString());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }





    }
}