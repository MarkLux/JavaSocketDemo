package com.marklux.socket.cycle;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;

public class CycleSocketClient {
    private String serverHost;
    private int serverPort;
    private Socket socket;
    private OutputStream outputStream;

    public CycleSocketClient(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }

    public void connetServer() throws IOException {
        this.socket = new Socket(this.serverHost, this.serverPort);
        this.outputStream = socket.getOutputStream();
    }

    public void send(String message) throws IOException {
        String sendMsg = message + "\n"; // we mark \n as a end of line.
        try {
            this.outputStream.write(sendMsg.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
//        this.outputStream.close();
//        this.socket.shutdownOutput();
    }

    public static void main(String[] args) {
        CycleSocketClient cc = new CycleSocketClient("127.0.0.1", 9799);
        try {
            cc.connetServer();
            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                cc.send(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}