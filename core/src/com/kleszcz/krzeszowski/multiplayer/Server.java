package com.kleszcz.krzeszowski.multiplayer;

import com.kleszcz.krzeszowski.SendReceiveDataListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Elimas on 2015-12-06.
 */
public class Server implements Runnable {
    private SendReceiveDataListener sendReceiveDataListener;
    private int port;
    private ArrayList<Socket> socketsList = new ArrayList<>();

    public SendReceiveDataListener getSendReceiveDataListener() {
        return sendReceiveDataListener;
    }

    public void setSendReceiveDataListener(SendReceiveDataListener sendReceiveDataListener) {
        this.sendReceiveDataListener = sendReceiveDataListener;
    }

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server started");
            while (true) {
                Socket socket = serverSocket.accept();
                socketsList.add(socket);
                System.out.println("New client connected");
                new Thread(new ClientHandler(socketsList.size() + 1, socket)).start();
                Thread.sleep((int)(1000f / 30f));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
