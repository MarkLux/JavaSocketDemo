package com.marklux.socket.dual;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DualSocketServer {
    private int port;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket socket;
    private ServerSocket serverSocket;

    public DualSocketServer(int port) {
        this.port = port;
    }

    public void runServer() throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.socket = serverSocket.accept();
        this.inputStream = socket.getInputStream();

        String message = new String(inputStream.readAllBytes(), "UTF-8");

        System.out.println("received message: " + message);

        this.socket.shutdownInput(); // 告诉客户端接收已经完毕，之后只能发送

        // write the receipt.

        this.outputStream = this.socket.getOutputStream();
        String receipt = "We received your message: " + message;
        outputStream.write(receipt.getBytes("UTF-8"));

        this.outputStream.close();
        this.socket.close();
    }

    public static void main(String[] args) {
        DualSocketServer ds = new DualSocketServer(9799);

        try {
            ds.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
