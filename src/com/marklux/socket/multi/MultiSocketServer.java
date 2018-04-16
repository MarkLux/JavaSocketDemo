package com.marklux.socket.multi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiSocketServer {
    private int port;
    private ServerSocket serverSocket;
    private Vector<UserStock> connections;
    private static final int MAX_THREAD_NUMBER = 10;
    private ExecutorService threadPool;

    public MultiSocketServer(int port) {
        this.port = port;
        this.threadPool = Executors.newFixedThreadPool(MAX_THREAD_NUMBER);
    }

    public void runServer() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
    }

    public void handleConnect(Socket socket) throws IOException {
        // 双方规定成功建立连接后的第一行消息为用户在聊天室中显示的名字
        Scanner sc = new Scanner(socket.getInputStream());
        String name = "NO_NAME";
        if (sc.hasNextLine()) {
            name = sc.nextLine();
        }
        this.connections.add(new UserStock(name,socket));
    }

    // 向当前聊天室内所有其他用户广播消息内容
    public void boardcast(String message) throws IOException {
        // 广播时若有其他用户加入可能引发同步问题
        for (UserStock userStock:this.connections) {
            OutputStream outputStream = userStock.getSocket().getOutputStream();
            outputStream.write(message.getBytes("UTF-8"));
        }
    }
}
