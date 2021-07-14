package com.example.leetcode;

public class UniqueStr {

    public static void main(String[] args) {
        String str = "abcdefglsj";
        String[] arr = str.split("");
        boolean flag = false;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[0] == arr[j]){
                    flag = true;
                }
            }
        }
        if (flag){
            System.out.println("true");
        }else System.out.println("false");
    }
}
