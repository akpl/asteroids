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
    private ArrayList<ClientHandler> clientsList = new ArrayList<>();

    public SendReceiveDataListener getSendReceiveDataListener() {
        return sendReceiveDataListener;
    }

    public void setSendReceiveDataListener(SendReceiveDataListener sendReceiveDataListener) {
        this.sendReceiveDataListener = sendReceiveDataListener;
    }

    public ArrayList<ClientHandler> getClientsList() {
        return clientsList;
    }

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                ClientHandler clientHandler = new ClientHandler(this, clientsList.size() + 1, socket);
                clientsList.add(clientHandler);
                Thread t = new Thread(clientHandler);
                t.start();
                Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
