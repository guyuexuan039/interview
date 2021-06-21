package com.example.threadSafe;

import java.util.concurrent.CountDownLatch;

/**
 * 以下代码保证了三个线程能够同时并发执行
 */
public class ThreadSafeDemo {

    public static void main(String[] args) throws InterruptedException{

        int size = 3;    // 并发线程数
        ThreadSafeDemo threadSafeDemo = new ThreadSafeDemo();

        CountDownLatch countDownLatch = new CountDownLatch(1);  // CountDownLatch保证线程同时执行
        for (int i = 0; i < size; i++){
            new Thread(()-> {
                try {
                    countDownLatch.await(); // 三个线程都会在这里排队等候枪响
                    System.out.println(System.currentTimeMillis());
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(5000); // 主线程sleep只是为了保证CPU准备上述线程的时间，
        countDownLatch.countDown(); // 该行代码相当于是跑步指挥官打枪，预备跑步了
    }
}
