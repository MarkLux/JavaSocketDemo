package com.marklux.socket.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 9711);
            OutputStream outputStream = socket.getOutputStream();
            int length = 1024;
            System.out.println("length: " + Integer.toBinaryString(length));
            outputStream.write(length>>8);
            System.out.println("first: " + Integer.toBinaryString(length>>8));
            outputStream.write(length);
            System.out.println("second: " + Integer.toBinaryString(length));
            outputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
