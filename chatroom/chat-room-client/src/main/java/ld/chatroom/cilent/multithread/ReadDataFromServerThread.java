package ld.chatroom.cilent.multithread;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 从服务器读取信息
 * Author:li_d
 * Created:2019/4/10
 */
public class ReadDataFromServerThread extends Thread{
    private final Socket client;
    //通过构造方法传入通信的Socket
    public ReadDataFromServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        //获取输入流，读取服务器发来的信息
        try {
            InputStream clientInput = client.getInputStream();
            Scanner scanner = new Scanner(clientInput);
            while (true) {//循环实现不断读取服务器信息
                String message = scanner.nextLine();//按行读取
                System.out.println("来自服务器的消息:" + message);
                if (message.equals("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
