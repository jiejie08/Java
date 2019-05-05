package ld.chatroom.cilent.single;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author:li_d
 * Created:2019/2/23
 */
public class SingleThreadCilent {
    public static void main(String[] args) {
        //指定ip和端口号
        String sreverip = "127.0.0.1";
        Integer port = 7080;
        /*1.准备Socket对象，连接到服务器端*/
        Socket cilent = null;
        try {
            cilent = new Socket(sreverip,port);
            System.out.println("连接上服务器，服务器地址为："+cilent.getInetAddress());

            /*2.发送数据*/
            OutputStreamWriter cilentData = new OutputStreamWriter(cilent.getOutputStream());
            cilentData.write("我是客户端\n");
            cilentData.flush();

            /*3.接收数据*/
            Scanner serverData = new Scanner(cilent.getInputStream());//获取客户端的字节流
            String svData = serverData.next();//将字节流转换为字符流
            System.out.println("这是服务器发送的数据："+svData);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

