package com.mybatis.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by zj on 2017/8/25.
 * create in CISDI_DATE firstly
 */
public class FileUtil {
    public File getFileByName(String fileName) {
        String classPath = getClassPath();
        File file = new File(classPath + "\\" + fileName);
        return file;
    }

    public String getClassPath() {
        URL url = getClass().getResource("/");
        File directory = null;
        try {
            directory = new File(url.toURI());//这一步把/编程\
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String classPath = directory.getAbsolutePath();
        return classPath;
    }

    public void cleanup(BufferedReader br, BufferedWriter bw, File tempFile) {
        try {
            br.close();//提前退出如果不关闭流则会导致后面进来的线程无法删除文件
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean deleteResult = tempFile.delete();
        System.out.println("临时文件删除结果： " + deleteResult);
    }

    public void writeToFile(File file, String content) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
