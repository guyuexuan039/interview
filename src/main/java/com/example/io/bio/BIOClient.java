package com.example.io.bio;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * BIO 模拟客户端
 * 服务器启动ServerSoket。
 * 客户端启动Socket与服务器通信，默认情况下服务器需要对每个客户端建立一个线程与之通信。
 * 客户端发出请求与服务器通信。
 * 如果请求成功，客户端会等待请求结束后继续执行。
 * <p>
 * 参考自 https://blog.csdn.net/qq_42216791/article/details/107316926
 */
public class BIOClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 9000);

        // 读取 classpath 下的 文件
        String fileName = BIOClient.class.getClassLoader().getResource("1.txt").getPath();

        InputStream inputStream = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCount = inputStream.read(buffer)) >= 0) {
            total += readCount;
            dataOutputStream.write(buffer);
        }
        System.out.println("发送总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime));
        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
