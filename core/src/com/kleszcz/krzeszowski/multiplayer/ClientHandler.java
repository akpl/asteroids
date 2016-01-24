package com.kleszcz.krzeszowski.multiplayer;

import com.badlogic.gdx.Gdx;
import com.kleszcz.krzeszowski.ClientDisconnectListener;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ConcurrentModificationException;

/**
 * Created by Elimas on 2015-12-06.
 */
public class ClientHandler implements Runnable {
    private Server server;
    private int clientId;
    private Socket socket;
    private Object lastObject;

    public ClientHandler(Server server, int clientId, Socket socket) {
        this.server = server;
        this.clientId = clientId;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeInt(clientId);
            out.flush();
            out.reset();
            Runnable receiverRunnable = new Runnable() {
                @Override
                public void run() {
                    while (!socket.isClosed()) {
                        try {
                            Object object = in.readObject();
                            lastObject = object;
                            server.getSendReceiveDataListener().onDataReceived(object);
                        } catch (IOException | ClassNotFoundException e) {
                            if (e instanceof SocketException) {
                                if (((SocketException) e).getMessage().equals("Connection reset")) {
                                    System.out.println("Connection closed with client: " + clientId);
                                    if (server.getSendReceiveDataListener() instanceof ClientDisconnectListener) {
                                        ((ClientDisconnectListener) server.getSendReceiveDataListener()).clientDisconnect(clientId);
                                    }
                                    try {
                                        socket.close();
                                    } catch (IOException e1) {
                                        //e1.printStackTrace();
                                    }
                                    break;
                                } else if (((SocketException) e).getMessage().equals("Socket closed")) break;
                            } else e.printStackTrace();
                        }
                    }
                }
            };
            Thread threadReceiver = new Thread(receiverRunnable);
            threadReceiver.start();
            while (!socket.isClosed()) {
                Object object = server.getSendReceiveDataListener().sendData();
                if (object != null) {
                    try {
                        out.writeObject(object);
                        out.flush();
                        out.reset();
                    } catch (IOException e) {
                        if (((SocketException) e).getMessage().equals("Socket closed") || ((SocketException) e).getMessage().equals("Connection reset by peer: socket write error")) break;
                        else e.printStackTrace();
                    } catch (ConcurrentModificationException e) {
                        System.out.println("Concurrent exception");
                    }
                    Thread.sleep(15);
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getLastObject() {
        return lastObject;
    }
}
