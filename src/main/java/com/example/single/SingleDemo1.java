package com.example.single;

/**
 * DCL（Double Check Lock）单例 演示
 */
public class SingleDemo1 {

    private SingleDemo1(){

    }

//    private static SingleDemo1 singleDemo1 = new SingleDemo1();
    private static SingleDemo1 singleDemo1;

    public static SingleDemo1 getSingleDemo1(){
        if (null == singleDemo1){
            synchronized (SingleDemo1.class){
                if (null == singleDemo1){
                    singleDemo1 = new SingleDemo1();
                }
            }

        }
        return singleDemo1;
    }
}
