package ld.chatroom.server.multithread;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author:li_d
 * Created:2019/4/03
 */
public class MultiThreadServer {
    public static void main(String[] args) {
        int port = 7080;
        int num = 0;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("端口参数不正确，采用默认端口" + port);
            }
        }
        final ExecutorService executorService = Executors.newFixedThreadPool(2);//多线程池，固定的，调度
        try {
            ServerSocket serverSocket = new ServerSocket(7080);
            System.out.println("等待客户端连接...\n");
            while (true){//一直循环等待客户端连接
                Socket cilent = serverSocket.accept();
                num++;
                if (num > 2){
//                    OutputStreamWriter SreverData = new OutputStreamWriter(cilent.getOutputStream());
//                    SreverData.write("抱歉，由于当前在线用户较多，服务器繁忙，请稍后注册~");
                }else{
                    executorService.submit(new ExecuteClient(cilent));//调用业务
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
