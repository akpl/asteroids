package com.kleszcz.krzeszowski.multiplayer;

import java.io.*;
import java.net.Socket;

/**
 * Created by Elimas on 2015-12-06.
 */
public class ClientHandler implements Runnable {
    private int clientId;
    private Socket socket;

    public ClientHandler(int clientId, Socket socket) {
        this.clientId = clientId;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), false);
            while (true) {
                //String data = in.readUTF();
                out.println("id");
                out.println(clientId);
                out.flush();
                Thread.sleep((int)(1000f / 30f));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
