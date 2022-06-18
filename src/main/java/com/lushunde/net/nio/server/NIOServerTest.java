package com.lushunde.net.nio.server;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 1、客户端连接到服务端的时候ServerSocketChannel 获取连接生成SocketChannel
 * 2、服务端创建一个Selector.open,
 * 3、刚才生成的socketChannel 注册到register到selector中
 * 4、注册后返回一个SelectedKey  由selector管理
 * 5 、selector 进行监听 select,selectNow 返回有事件发生的socketchannel个数
 *  6、获取selector所管理的有事件发生的selectedKeys 集合，然后循环遍历所有的事件 通过selectedKey反向获取SocketChannel
 *  7、通过获取的socketchannel完成对应事件的处理
 *
 */
public class NIOServerTest {

    public static void main(String[] args) throws IOException {
        //创建server
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定一个端口
        serverSocketChannel.socket().bind(new InetSocketAddress(8000));

        //创建一个selector
        Selector selector = Selector.open();
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //将ServerSocketChannel 注册到选择器中 关心Accept事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("成功 = ");

        //循环等待客户端连接
        while (true){

            System.out.println("循环 ");


            //阻塞等待关心的channel事件发生
            int select = selector.select();
            System.out.println("循环 select = "+ select);
            //如果有事件发生select>0 获取到相关事件的集合
            Iterator<SelectionKey> iterator = selector.selectedKeys()
                    .iterator();

            System.out.println("iterator = " + iterator);

            while (iterator.hasNext()){

                //获取发生事件的key
                SelectionKey selectionKey=iterator.next();
                System.out.println("进入循环 selectionKey= " + selectionKey);

                //如果是连接请求事件
                if(selectionKey.isAcceptable()&&selectionKey.isValid()){

                    System.out.println("NIOServerTest.main   8888888888");

                    ServerSocketChannel serverSocketChannelAccept=(ServerSocketChannel)selectionKey.channel();
                    //给客户端生成一个SocketChannel  非阻塞
                    SocketChannel socketChannel = serverSocketChannelAccept.accept();
                    System.out.println("客户端链接成功 生成一个sockeychannel"+socketChannel.hashCode());
                    //设置客户端为非阻塞的
                    socketChannel.configureBlocking(false);
                    //将与客户端连接的socketChannel也注册到selector中 同时给 Channel关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ,
                            ByteBuffer.allocate(1024));


                    System.out.println("NIOServerTest.main   88888888888888   end");

                }else if(selectionKey.isReadable()&&selectionKey.isValid()){

                    System.out.println("NIOServerTest.main   99999999");


                    //通过key反向获取对应的channel进行读取
                    SocketChannel readSocketChannel=(SocketChannel) selectionKey.channel();
                    //获取该channel关联的buffer
                    ByteBuffer buffer=(ByteBuffer) selectionKey.attachment();


                    int read = readSocketChannel.read(buffer);

                    System.out.println("read = " + read);

                    if(read != -1) {
                        System.out.println("客户端发送的数据：" + new String(buffer.array(), 0, read));
                        System.out.println("客户端发送的数据：" + buffer.toString());


                        System.out.println("NIOServerTest.main   9999999999   end");
                    }{
                        System.out.println("NIOServerTest.main  没有数据");
                    }

                }
                //手动将selectionKey从集合中移除 防止重复操作
                iterator.remove();

                System.out.println(" remove  end   selectionKey = " + selectionKey);
            }

            System.out.println(" remove  end   iterator  = " + iterator);

        }




    }


}
