package com.example.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO 模拟 服务端
 * 服务器启动ServerSoket。
 * 客户端启动Socket与服务器通信，默认情况下服务器需要对每个客户端建立一个线程与之通信。
 * 客户端发出请求与服务器通信。
 * 如果请求成功，客户端会等待请求结束后继续执行。
 * 参考自 https://blog.csdn.net/qq_42216791/article/details/107316926
 */
public class BIOServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) { // 监听，等待客户端连接
            System.out.println("等待客户端连接...");
            Socket socket = serverSocket.accept();  // 阻塞方法，服务端在此等候接收 客户端请求
            // 有请求进来后，就创建一个线程去执行
            new Thread(new Runnable() { // 这是 匿名内部类的写法
                @Override
                public void run() {
                    try {
                        handler(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


    // 编写一个handler方法，与客户端通讯
    private static void handler(Socket socket) throws IOException {

        // 通过socket获取输入流
        try {
            System.out.println("线程信息 id=" + Thread.currentThread().getId() + "线程名字=" + Thread.currentThread().getName());
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];

            // 循环的读取客户端发送的数据
            while (true) {
                System.out.println("线程信息 id=" + Thread.currentThread().getId() + "线程名字=" + Thread.currentThread().getName());

                int read = inputStream.read(bytes);
                if (read != -1) {
                    // 输出客户端发送的数据
                    System.out.println(new String(bytes, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭和客户端的连接
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
