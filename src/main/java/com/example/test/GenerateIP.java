package com.example.test;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * IP地址第一位填写104，第二位填16~31之间的一个数字，第三位填0~255之间的一个数字，第四位填1~254之间的数字。
 * 理论上有上百万个IP地址，但是有不少是不能用的，遇到不能用的再换一个。
 */
public class GenerateIP {

    public static void main(String[] args) throws IOException {
        String first = "104";
        for (int i = 0; i < 14 * 254 * 252; i++) {
            // 第二位生成规则
            String second = String.valueOf(ThreadLocalRandom.current().nextInt(16, 32));
            // 第三位
            String third = String.valueOf(ThreadLocalRandom.current().nextInt(0, 256));
            // 第四位
            String fourth = String.valueOf(ThreadLocalRandom.current().nextInt(1, 255));
            // IP
            String ip = first + "." + second + "." + third + "." + fourth;
//            System.out.println(first + "." + second + "." + third + "." + fourth);
            ////////// 控制台输出写入到一个文件中
            bufferedWriterTest("E:/temp/free-ss-ip.txt", "\n" + ip);

            //////////////////// 从文件中读取内容
//            try {
//                BufferedReader reader = new BufferedReader(new FileReader(
//                        "E:/temp/free-ss-ip.txt"));// 读出文件
//                while (reader.read() >= 0) {
//                    System.out.println(reader.readLine());
//                }
//                reader.close();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            PrintStream out = new PrintStream("E:/temp/free-ss-ip.txt");
//            System.setOut(out);
//            System.out.println(ip);
        }

    }


    /**
     * 方法二:使用 BufferedWriter 写文件
     * @param filepath 文件目录
     * @param content  待写入内容
     * @throws IOException
     */
    private static void bufferedWriterTest(String filepath, String content) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(
                new FileWriter(filepath, true))) { // 追加内容
            bufferedWriter.write(content);
        }
    }
}
