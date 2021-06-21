package com.example.threadSafe;

import java.util.concurrent.Semaphore;

/**
 * 三个线程交替执行
 */
public class SemaphoreDemo {

    // 利用信号量限制
    private static Semaphore s1 = new Semaphore(1);
    private static Semaphore s2 = new Semaphore(1);
    private static Semaphore s3 = new Semaphore(1);

    public static void main(String[] args) {

        try {
            // 首先调用S2为 acquire 状态
            s1.acquire();
            s2.acquire();
//            s2.acquire();  调用s1或者s2先占有一个
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            while (true){
                try {
                    s1.acquire();   // s1 获取信号量
                    Thread.sleep(500);
                }catch (InterruptedException e ){
                    e.printStackTrace();
                }
                System.out.println("A");
                s2.release();   // 释放s2的信号量
            }
        }).start();

        new Thread(() -> {
            while (true){
                try {
                    s2.acquire();   // s2 获取信号量
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("B");
                s3.release();   // 释放s3的信号量
            }
        });

        new Thread(() -> {
            while (true){
                try {
                    s3.acquire();   // s3 获取信号量
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("C");
                s1.release();
            }
        });
    }
}
