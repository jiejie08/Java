package ld.chatroom.server.multithread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器处理客户端请求的线程
 * 问题：参数校验
 * 服务端处理客户端连接的任务
 * 1、注册
 * 2、私聊
 * 3、群聊
 * 4、退出
 * 5、显示当前在线用户
 * 6、统计用户活跃度
 * Author:li_d
 * Created:2019/3/15
 */
public class ExecuteClient implements Runnable {
    //使用ConcurrentHashMap来保存所有连接的客户端信息
    private static final Map<String,Socket> ONLINE_USER_MAP = new ConcurrentHashMap<>();//k-v模型，多线程有可能会产生并发问题，
    //因此，需要一个安全的Map集合
    //要存放当前在线的所有创建用户，如果是一个private Map,这个Map隶属于client的成员属性，这样是属于对象的，而在此针对用户每次
    //都有new一个新的对象，就意味着这个Map有多个，就不能共享了，因此需要static
    private final Socket client;//final修饰，防止被修改，跟客户端交互，把Socket传过来

    public ExecuteClient(Socket cilent) {
        this.client = cilent;
    }//通过构造方法传入通信的Socket

    public void run() {//实现功能
        try {
            //1、获取输入流不断的读取用户发来的信息
            InputStream clientInput = this.client.getInputStream();
            Scanner scanner = new Scanner(clientInput);
            while (true) {
                String line = scanner.nextLine();

                /**
                 * 1.注册：userName:<name>
                 * 2.私聊：private:<name>:<message>
                 * 3.群聊：grup:<message>
                 * 4.退出：bye
                 */
                if (line.startsWith("userName")) {//小缺陷：比如userNameA:,不严谨，要判断第0个是useName再去注册
                    String userName = line.split("\\:")[1];//处理字符串，拆分出用户名，按:拆分的第二个元素为用户名
                    //:是正则表达式里面的元素，要加转义字符
                    this.register(userName, client);//调用注册方法
                    continue;//跳出本次循环继续
                }
                if (line.startsWith("private")) {
                    String[] segments = line.split("\\:");
                    String userName = segments[1];
                    String message = segments[2];
                    this.privateChat(userName, message);
                    continue;
                }
                if (line.startsWith("group")) {
                    String message = line.split("\\:")[1];
                    this.groupChat(message);
                    continue;
                }
                if (line.equals("bye")) {
                    this.quit();
                    break;
                }

            }

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void quit() {//退出要注意当前用户退出
        String currentUserName = this.getCurrentUserName();
        System.out.println("用户:" + currentUserName + "下线");
        Socket socket = ONLINE_USER_MAP.get(currentUserName);
        this.sendMessage(socket, "bye");
        ONLINE_USER_MAP.remove(currentUserName);
        printOnlineUser();
    }

    /**
     * 群聊
     * @param message  要发送的群聊信息
     */
    private void groupChat(String message) {
        for (Socket socket : ONLINE_USER_MAP.values()) {
            if (socket.equals(this.client)) {
                continue;
            }
            this.sendMessage(socket, this.getCurrentUserName() + " 说：" + message);
        }
    }

    /**
     * 私聊
     * @param userName  私聊的用户名
     * @param message  私聊的信息
     */
    private void privateChat(String userName, String message) {
        String currentUserName = this.getCurrentUserName();
        Socket target = ONLINE_USER_MAP.get(userName);
        if (target != null) {//私聊必须判断这个人在不在
            this.sendMessage(target, currentUserName + " 对你说： " + message);
        }
    }

    /**
     * 用户注册
     * @param userName 用户名
     * @param client  对应的Socket
     */
    private void register(String userName, Socket client) {
        System.out.println(userName + "加入到聊天室 " + client.getRemoteSocketAddress());
        ONLINE_USER_MAP.put(userName, client);
        printOnlineUser();
        sendMessage(this.client, userName + "注册成功!");
    }
    //获取当前用户名（公共）
    private String getCurrentUserName() {
        String currentUserName = "";
        for (Map.Entry<String, Socket> entry : ONLINE_USER_MAP.entrySet()) {
            if (this.client.equals(entry.getValue())) {
                currentUserName = entry.getKey();
                break;
            }
        }
        return currentUserName;
    }

    private void sendMessage(Socket socket, String message) {//发送数据的公共代码
        try {
            OutputStream clientOutput = socket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(clientOutput);
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printOnlineUser() {
        System.out.println("当前在线人数：" + ONLINE_USER_MAP.size() + " 用户名如下列表：");
        for (Map.Entry<String, Socket> entry : ONLINE_USER_MAP.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}
