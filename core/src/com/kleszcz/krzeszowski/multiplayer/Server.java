package com.kleszcz.krzeszowski.multiplayer;

import com.kleszcz.krzeszowski.SendReceiveDataListener;
import com.kleszcz.krzeszowski.game.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Elimas on 2015-12-06.
 */
public class Server implements Runnable {
    private SendReceiveDataListener sendReceiveDataListener;
    private int port;
    private ArrayList<ClientHandler> clientsList = new ArrayList<>();
    private int clientId = 2;
    private ServerSocket serverSocket;
    private ReentrantLock writeLock;

    public ReentrantLock getWriteLock() {
        return writeLock;
    }

    public void setWriteLock(ReentrantLock writeLock) {
        this.writeLock = writeLock;
        for (ClientHandler clientHandler : clientsList) {
            clientHandler.setWriteLock(writeLock);
        }
    }

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
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected. Client id: " + clientId);
                ClientHandler clientHandler = new ClientHandler(this, clientId, socket);
                clientHandler.setWriteLock(writeLock);
                clientsList.add(clientHandler);
                Thread t = new Thread(clientHandler);
                t.start();
                clientId++;
                Thread.sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            serverSocket.close();
            for (ClientHandler client : clientsList) {
                try {
                    client.shutdown();
                } catch (Exception e) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
