package com.marklux.socket.dual;

import java.io.*;
import java.net.Socket;

public class DualSocketClient {
    private String host;
    private int port;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket socket;

    public DualSocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendMessage(String message) throws IOException {
        this.socket = new Socket(host,port);
        this.outputStream = socket.getOutputStream();
        this.outputStream.write(message.getBytes("UTF-8"));
        this.socket.shutdownOutput(); // 告诉服务器，所有的发送动作已经结束，之后只能接收
        this.inputStream = socket.getInputStream();
        String receipt = new String(inputStream.readAllBytes(), "UTF-8");
        System.out.println("got receipt: " + receipt);
        this.inputStream.close();
        this.socket.close();
    }

    public static void main(String[] args) {
        DualSocketClient dc = new DualSocketClient("127.0.0.1", 9799);
        try {
            dc.sendMessage("Hi from client");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
