package ld.chatroom.server.single;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Author:li_d
 * Created:2019/3/24
 */
public class SingleThreadServer {
    public static void main(String[] args){
        try {
            /*1.准备ServerSocket对象和端口号，建立服务端基站*/
            ServerSocket serverSocket = new ServerSocket(7080);
            System.out.println("等待客户端连接\n");

            /*2.accept()方法，接收客户端连接，并返回客户端的Socket对象，否则线程将一直阻塞于此处*/
            Socket cilent = serverSocket.accept();
            System.out.println("有新的客户端连接，端口号为："+cilent.getPort());


            /*3.接收客户端的数据*/
            Scanner CilentData = new Scanner(cilent.getInputStream());
            String userData = CilentData.next();
            System.out.println("这是客户端发送的数据:"+userData);

            /*4.向客户端发送数据*/
            OutputStreamWriter SreverData = new OutputStreamWriter(cilent.getOutputStream());
            SreverData.write("你好，你有什么需要帮助的？");
            SreverData.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
