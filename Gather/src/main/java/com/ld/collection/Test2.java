package com.ld.collection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Author:li_d
 * Created:2019/3/24
 */
public class Test2 {
    public static void main(String[] args) throws Exception{
        File file = new File("C:\\Users\\Lenovo\\Desktop\\test.properties");
        Properties properties = new Properties();
        //写入文件
        /*properties.setProperty("user","root");
        properties.setProperty("password","123456");
        properties.store(new FileOutputStream(file),"uesr and password");*/
        //读取文件
        properties.load(new FileInputStream(file));
        System.out.println(properties.getProperty("user"));
        System.out.println(properties.getProperty("password"));
    }
}
