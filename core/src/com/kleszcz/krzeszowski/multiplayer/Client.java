package com.kleszcz.krzeszowski.multiplayer;

import com.kleszcz.krzeszowski.SendReceiveDataListener;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by Elimas on 2015-12-06.
 */
public class Client implements Runnable {
    private SendReceiveDataListener sendReceiveDataListener;
    private String host;
    private int port;
    private Object lastObject;

    public SendReceiveDataListener getSendReceiveDataListener() {
        return sendReceiveDataListener;
    }

    public void setSendReceiveDataListener(SendReceiveDataListener sendReceiveDataListener) {
        this.sendReceiveDataListener = sendReceiveDataListener;
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Runnable receiverRunnable = () -> {
                while (true) {
                    try {
                        Object object = in.readObject();
                        lastObject = object;
                        //sendReceiveDataListener.onDataReceived(object);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread threadReceiver = new Thread(receiverRunnable);
            threadReceiver.start();
            while (true) {
                Object object = sendReceiveDataListener.sendData();
                out.writeObject(object);
                out.flush();
                out.reset();
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
