package com.example.forkjoin;

import java.util.Arrays;

/**
 * 合并两个有序数组
 * 思路一：先合并两个数组为一个数组后，再对这个数组进行排序
 */
public class JoinArray {

    public static void main(String[] args) {

        int[] a1 = {1, 3, 5, 7, 13};
        int[] a2 = {2, 4, 6, 12, 14};
        int[] dest = new int[a1.length + a2.length];

        // 思路一: 先合并两个数组为一个数组后，再对这个数组进行排序
        dest = Arrays.copyOf(a1, a1.length + a2.length);
        System.arraycopy(a2, 0, dest, a1.length, a2.length);

        for (int i = 0; i < dest.length; i++) {
            for (int j = i + 1; j < dest.length; j++) {
                if (dest[i] >= dest[j]){
                    int temp = dest[j];
                    dest[j] = dest[i];
                    dest[i] = temp;
                }
            }
        }

        System.out.println(Arrays.toString(dest));

        // 使用思路二实现
        System.out.println(Arrays.toString(sort(a1, a2)));

    }


    /**
     * 思路二: 利用有序，
     *     假设两个源数组的长度不一样，那么假设其中短的数组用完了,即全部放入到新数组中去了,那么长数组中剩下的那一段就可以直接拿来放到新数组中去了
     */
    public static int[] sort(int[] a, int[] b){

        int[] c = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length){
            if (a[i] >= b[j]){
                c[k++] = b[j++];
            }else{
                c[k++] = a[i++];
            }
        }

        while (j < b.length){
            c[k++] = b[j++];
        }

        while (i < a.length){
            c[k++] = a[i++];
        }

        return c;
    }
}
