package com.example.forkjoin;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 如何对一个字符串快速进行排序？
 * 使用 ForkJoin 框架实现高并发下的排序
 * ForkJoin 框架 是用于 大数据 Hadoop 中的 MapReduce 中，在 j2ee 中使用较少
 */

public class MergeTest {

    private static int MAX = 100;

    private static int inits[] = new int[MAX];

    // 随即队列初始化
    static {
        Random r = new Random();
        for (int i = 1; i <= MAX; i++) {
            inits[i - 1] = r.nextInt(1000);
        }
    }

    public static void main(String[] args) throws Exception {

        // 正式开始
        long beginTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        MyTask task = new MyTask(inits);
        ForkJoinTask<int[]> taskResult = pool.submit(task);
        try {
            int[] ints = taskResult.get();
            System.out.println(Arrays.toString(ints));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时= " + (endTime - beginTime) + "ms");
    }


    /**
     * 单个排序的子任务
     */

    static class MyTask extends RecursiveTask<int[]> {

        private int[] source;

        public MyTask(int[] source) {

            this.source = source;
        }

        @Override
        protected int[] compute() {

            int sourceLen = source.length;
            // 如果条件成立，说明任务中要进行排序的集合还不够小
            if (sourceLen > 2) {
                int midIndex = sourceLen / 2;
                // 拆分成两个子任务
                MyTask task1 = new MyTask(Arrays.copyOf(source, midIndex));
                task1.fork();
                MyTask task2 = new MyTask(Arrays.copyOfRange(source, midIndex, sourceLen));
                task2.fork();
                // 将两个有序的数组，合并成一个有序的数组
                int[] result1 = task1.join();
                int[] result2 = task2.join();
                int[] mer = joinInts(result1, result2);
                return mer;
            }
            // 否则说明集合中只有一个或者两个元素，可以进行这两个元素的比较排序了
            else {
                // 如果条件成立，说明数组中只有一个元素，或者是数组中的元素都已经排列好位置了
                if (sourceLen == 1 || source[0] <= source[1]) {
                    return source;
                } else {
                    int[] target = new int[sourceLen];
                    target[0] = source[1];
                    target[1] = source[0];
                    return target;
                }
            }
        }


        /**
         * 该方法用于合并两个有序集合
         */
        private static int[] joinInts(int[] a, int[] b) {

            int[] c = new int[a.length + b.length];
            int i = 0, j = 0, k = 0;
            while (i < a.length && j < b.length) {
                if (a[i] >= b[j]) {
                    c[k++] = b[j++];
                } else {
                    c[k++] = a[i++];
                }
            }

            while (j < b.length) {
                c[k++] = b[j++];
            }

            while (i < a.length) {
                c[k++] = a[i++];
            }

            return c;
        }
    }
}
