package com.marklux.socket.length;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class LengthSocketClient {
    private String host;
    private int port;
    private Socket socket;
    private OutputStream outputStream;

    public LengthSocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connetServer() throws IOException {
        this.socket = new Socket(host,port);
        this.outputStream = socket.getOutputStream();
    }

    public void sendMessage(String message) throws IOException {
        // 首先要把message转换成bytes以便处理
        byte[] bytes = message.getBytes("UTF-8");
        // 接下来传输两个字节的长度，依然使用移位实现
        int length = bytes.length;
        this.outputStream.write(length >> 8); // write默认一次只传输一个字节
        this.outputStream.write(length);
        // 传输完长度后，再正式传送消息
        this.outputStream.write(bytes);
    }

    public static void main(String[] args) {
        LengthSocketClient lc = new LengthSocketClient("127.0.0.1",9799);
        try {
            lc.connetServer();
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()) {
                lc.sendMessage(sc.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
