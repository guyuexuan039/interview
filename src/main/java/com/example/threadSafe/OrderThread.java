package com.example.threadSafe;

/**
 * 三个线程依次执行
 * 只有a开头的打印输出完成后，才会执行b开头的，继而是C开头的
 */
public class OrderThread {

    static volatile int ticket = 1;

    public static void main(String[] args) {
        Thread t1 = new Thread(()-> {
           while (true){
               if (ticket == 1){
                   try {
                       Thread.sleep(100);
                       for (int i = 0; i < 10; i++) {
                           System.out.print("a" + i + " ");
                       }
                   }catch (InterruptedException e){
                       e.printStackTrace();
                   }
                   ticket = 2;
                   System.out.println();
                   return;
               }
           }
        });


        Thread t2 = new Thread(()-> {
            while (true){
                if (ticket == 2){
                    try {
                        Thread.sleep(100);
                        for (int i = 0; i < 10; i++) {
                            System.out.print("b" + i + " ");
                        }
                    }catch (InterruptedException e ){
                        e.printStackTrace();
                    }
                    ticket = 3;
                    System.out.println();
                    return;
                }
            }
        });


        Thread t3 = new Thread(() -> {
            while (true){
                if (ticket == 3){
                    try {
                        Thread.sleep(100);
                        for (int i = 0; i < 10; i++) {
                            System.out.print("c" + i + " ");
                        }
                    }catch (InterruptedException e ){
                        e.printStackTrace();
                    }
                    ticket = 1;
                    return;
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
