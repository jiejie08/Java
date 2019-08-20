package ld.chatroom.cilent.multithread;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 向服务器发送信息
 * Author:li_d
 * Created:2019/4/11
 */
public class WriteDataToServerThread extends Thread{
    private final Socket client;
    //通过构造方法传入通信的Socket
    public WriteDataToServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            //获取输出流，向服务器发送消息
            OutputStream clientOutput = client.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(clientOutput);
            //获取键盘输入，向服务器发送消息
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("请输入消息：");
                String message = scanner.nextLine();

                //给服务器发数据
                writer.write(message + "\n");
                writer.flush();

                if (message.equals("bye")) {
                    //表示客户端要关闭
                    client.close();
                    break;//要加break,否则close之后还在循环
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
