package com.example.vol;

public class VolatileDemo1 {

    public static volatile boolean flag = true;

    public static void main(String[] args) {

        new Thread(()-> {
            while (flag){

            }
            System.out.println("==============End of Thread1==============");
        }).start();

        try {
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println(" turn flag off");
        flag = false;
        System.out.println(flag);
    }
}
