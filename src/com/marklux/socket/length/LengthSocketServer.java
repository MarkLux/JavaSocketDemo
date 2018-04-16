package com.marklux.socket.length;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class LengthSocketServer {
    private int port;
    private InputStream inputStream;
    private Socket socket;
    private ServerSocket serverSocket;

    public LengthSocketServer(int port) {
        this.port = port;
    }

    public void runServer() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        this.socket = serverSocket.accept();
        this.inputStream = socket.getInputStream();
        byte[] bytes;
        while (true) {
            // 先读第一个字节
            int first = inputStream.read();
            if (first == -1) {
                // 如果是-1，说明输入流已经被关闭了，也就不需要继续监听了
                this.socket.close();
                break;
            }
            // 读取第二个字节
            int second = inputStream.read();

            int length = (first << 8) + second; // 用位运算将两个字节拼起来成为真正的长度

            bytes = new byte[length]; // 构建指定长度的字节大小来储存消息即可

            inputStream.read(bytes);

            System.out.println("receive message: " + new String(bytes,"UTF-8"));
        }
    }

    public static void main(String[] args) {
        LengthSocketServer ls = new LengthSocketServer(9799);
        try {
            ls.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
