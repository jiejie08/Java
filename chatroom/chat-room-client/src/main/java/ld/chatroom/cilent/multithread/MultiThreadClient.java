package ld.chatroom.cilent.multithread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author:li_d
 * Created:2019/4/08
 */
public class MultiThreadClient {
    public static void main(String[] args) {
        try {
            //0.通过命令行获取参数
            int port = 7080;
            if (args.length > 0) {
                try {
                    port = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    System.out.println("端口参数不正确，采用默认端口" + port);
                }
            }
            String host = "127.0.0.1";
            if (args.length > 1) {
                host = args[1];
                //host格式校验
            }
            //建立与服务器的连接
            final Socket socket = new Socket(host, port);

            //1.创建写线程，往服务器发送数据
            new WriteDataToServerThread(socket).start();
            //2.创建读线程，从服务器读取数据
            new ReadDataFromServerThread(socket).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
