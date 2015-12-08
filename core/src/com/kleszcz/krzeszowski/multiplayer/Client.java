package com.kleszcz.krzeszowski.multiplayer;

import com.kleszcz.krzeszowski.SendReceiveDataListener;

import java.io.*;
import java.net.Socket;

/**
 * Created by Elimas on 2015-12-06.
 */
public class Client implements Runnable {
    private SendReceiveDataListener sendReceiveDataListener;
    private int clientId;
    private String name;
    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), false);
            while (true) {
                String command = in.readLine();
                String value = in.readLine();
                try {
                    processCommand(command, value);
                } catch (Exception e) {
                    System.err.println("Error processing command");
                    e.printStackTrace();
                }
                Thread.sleep((int)(1000f / 30f));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processCommand(String command, String value) {
        switch (command) {
            case "id": clientId = Integer.parseInt(value); break;
            default: if (sendReceiveDataListener != null) sendReceiveDataListener.onDataReceived(command, value);
        }
    }
}
