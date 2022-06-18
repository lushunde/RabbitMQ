package com.lushunde.net.netty.servce;

import com.sun.deploy.net.HttpRequest;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.util.CharsetUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName NettyServer
 * @Description TODO 快来记录点什么吧，不要太懒了
 * @Author bellus
 * @Date 2022/6/18 19:57
 * @Version 1.0.0
 **/

public class NettyServer {

    private int port = 8080;

    private ServerSocket server;

    public NettyServer(int port) {
        this.port = port;
    }

    //Tomcat的启动入口
    public void start() {


        //Boss线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //Worker线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //2、创建Netty服务端对象
        ServerBootstrap server = new ServerBootstrap();

        try {
            //3、配置服务端参数
            server.group(bossGroup, workerGroup)
                    //配置主线程的处理逻辑
                    .channel(NioServerSocketChannel.class)
                    //子线程的回调处理，Handler
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel client) throws Exception {
                            //处理回调的逻辑

                            //用户自己的业务逻辑
                            client.pipeline().addLast(new StringMessageHandler());

                        }
                    })
                    //配置主线程分配的最大线程数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //启动服务
            ChannelFuture f = server.bind(this.port).sync();

            System.out.println("服务已启动，监听端口是: " + this.port);

            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }




    class StringMessageHandler extends SimpleChannelInboundHandler  {



        /**
         * @Description 接受数据
         * @Author Bellus
         * @Date 2022/6/18 20:49
         * @Version 1.0.0
         * @Param [channelHandlerContext, msg]
         * @return void
         **/
        @Override
        protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
            if(msg instanceof ByteBuf){
                ByteBuf  buff =  (ByteBuf)msg;

                String message = new String(buff.toString(CharsetUtil.UTF_8));

                if(message!=null && message.trim().length() > 0){
                    System.out.println("接受到客户端的 message = "+  message);

                    // 回血一个数据
                    channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("hello client", CharsetUtil.UTF_8));


                }



            }else{

                System.out.println("msg = " + msg.getClass().getName());
                System.out.println("接受到客户端的 msg = "+ msg.toString());
            }
        }
    }




}
