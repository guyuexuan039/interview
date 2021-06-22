package com.example.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

    public static void main(String[] args) throws IOException {
        // 等到通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // 连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("没有连接上，可以做其它事情");
            }
        }
        // 连接成功
        String hello = "失忆老幺";
        ByteBuffer buffer = ByteBuffer.wrap(hello.getBytes());
        // 发送数据,将buffer数据写到channel
        socketChannel.write(buffer);
        System.in.read();
    }
}
