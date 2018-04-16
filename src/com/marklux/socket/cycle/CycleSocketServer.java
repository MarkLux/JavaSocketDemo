package com.marklux.socket.cycle;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * a cycling socket server that read messages in turn, and response with a recall.
 */

public class CycleSocketServer {

    private ServerSocket server;
    private Socket socket;
    private int port;
    private InputStream inputStream;
    private static final int MAX_BUFFER_SIZE = 1024;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public CycleSocketServer(int port) {
        this.port = port;
    }

    public void runServer() throws IOException {
        this.server = new ServerSocket(this.port);

        System.out.println("base socket server started.");

        this.socket = server.accept();
        // the code will block here till the request come.

        this.inputStream = this.socket.getInputStream();
        Scanner sc = new Scanner(this.inputStream);
        while (sc.hasNextLine()) {
            System.out.println("get info from client: " + sc.nextLine());
        }
        this.inputStream.close();
        socket.close();
    }

    public static void main(String[] args) {
        CycleSocketServer cs = new CycleSocketServer(9799);
        try {
            cs.runServer();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}

