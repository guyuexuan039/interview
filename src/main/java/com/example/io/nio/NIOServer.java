package com.example.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) throws IOException {
        // 创建serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 创建selector
        Selector selector = Selector.open();
        // 绑定一个端口6666.在服务器监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 把serverSocketChannel注册到selector 关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while (true) {
            // 一秒没有事件发生，没有事件发生
            if (selector.select(1000) == 0) {
                System.out.println("等待了一秒，无连接");
                continue;
            }
            // 如果返回的大于0,拿到selectionkey集合
            // 如果大于0，表示已回去到关注的事件
            // 通过selectionkeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                // 根据key对应的通道发生的事件做相应处理
                // 有新的客户端来连接了
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    // 将socketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    System.out.println("客户端连接成功======");
                    // 注册到selector上, 关注事件为读，给socketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {
                    // 通过key 反向获取对应的channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 获取到管理的Buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("form客户端" + new String(buffer.array()));
                }
                keyIterator.remove();
            }
        }
    }


}
