package com.example.mq;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * <pre>
 *     零拷贝的两种方式：mmap | transfile
 * </pre>
 */
public class ZeroCopy {

    public static void main(String[] args) throws IOException {

        File file = new File("E:\\secrecy-files\\test.txt");
        // mmap方式：映射为2K，生成的文件也就是2K
        final RandomAccessFile rw = new RandomAccessFile(file, "rw");
        final MappedByteBuffer map = rw.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 2048);
        map.put("mmap 拷贝方式 测试内容".getBytes());
        rw.close();


        try (FileInputStream fileOutputStream = new FileInputStream(file)) {
            FileChannel channel = fileOutputStream.getChannel();
            FileOutputStream out = new FileOutputStream("E:\\secrecy-files\\test_copy.txt");
            FileChannel outChannel = out.getChannel();
            // transferTo 方式：核心从一个管道直接到另一个管道
            channel.transferTo(0, file.length(), outChannel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
