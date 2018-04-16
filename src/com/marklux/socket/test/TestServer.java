package com.marklux.socket.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9711);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            int first = inputStream.read();
            System.out.println(Integer.toBinaryString(first));
            int second = inputStream.read();
            System.out.println(Integer.toBinaryString(second));
            int length = (first << 8) + second;
            System.out.println("length = " + length);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
