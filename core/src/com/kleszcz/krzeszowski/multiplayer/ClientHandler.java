package com.kleszcz.krzeszowski.multiplayer;

import com.badlogic.gdx.Gdx;

import java.io.*;
import java.net.Socket;

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
            Runnable receiverRunnable = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Object object = in.readObject();
                            lastObject = object;
                            server.getSendReceiveDataListener().onDataReceived(object);
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Thread threadReceiver = new Thread(receiverRunnable);
            threadReceiver.start();
            while (true) {
                Object object = server.getSendReceiveDataListener().sendData();
                if (object != null) {
                    try {
                        out.writeObject(object);
                        out.flush();
                        out.reset();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Thread.sleep(15);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getLastObject() {
        return lastObject;
    }
}
