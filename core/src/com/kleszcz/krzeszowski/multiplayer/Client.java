package com.kleszcz.krzeszowski.multiplayer;

import com.kleszcz.krzeszowski.ClientDisconnectListener;
import com.kleszcz.krzeszowski.SendReceiveDataListener;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Elimas on 2015-12-06.
 */
public class Client implements Runnable {
    private SendReceiveDataListener sendReceiveDataListener;
    private String host;
    private int port;
    private int clientId;
    private Object lastObject;
    private ReentrantLock writeLock;

    public ReentrantLock getWriteLock() {
        return writeLock;
    }

    public void setWriteLock(ReentrantLock writeLock) {
        this.writeLock = writeLock;
    }

    public SendReceiveDataListener getSendReceiveDataListener() {
        return sendReceiveDataListener;
    }

    public void setSendReceiveDataListener(SendReceiveDataListener sendReceiveDataListener) {
        this.sendReceiveDataListener = sendReceiveDataListener;
    }

    public int getClientId() {
        return clientId;
    }

    public Object getLastObject() {
        return lastObject;
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
            clientId = in.readInt();
            System.out.println("Client id: " + clientId);
            Runnable receiverRunnable = () -> {
                while (!socket.isClosed()) {
                    try {
                        Object object = in.readObject();
                        lastObject = object;
                        sendReceiveDataListener.onDataReceived(object);
                    } catch (IOException | ClassNotFoundException e) {
                        if (e instanceof SocketException) {
                            if (((SocketException) e).getMessage().equals("Connection reset")) {
                                System.out.println("Connection closed with server: 1");
                                lastObject = null;
                                if (sendReceiveDataListener instanceof ClientDisconnectListener) {
                                    ((ClientDisconnectListener) sendReceiveDataListener).clientDisconnect(1);
                                }
                                try {
                                    socket.close();
                                } catch (IOException e1) {
                                    //e1.printStackTrace();
                                }
                                break;
                            } else if (((SocketException) e).getMessage().equals("Socket closed")) break;
                        } else if (e instanceof EOFException) {
                            System.out.println("Connection closed with client: " + clientId);
                            if (sendReceiveDataListener instanceof ClientDisconnectListener) {
                                ((ClientDisconnectListener) sendReceiveDataListener).clientDisconnect(clientId);
                            }
                            try {
                                socket.close();
                            } catch (IOException e1) {
                                //e1.printStackTrace();
                            }
                            break;
                        } else e.printStackTrace();
                    }
                }
            };
            Thread threadReceiver = new Thread(receiverRunnable);
            threadReceiver.start();
            while (!socket.isClosed()) {
                try {
                    if (writeLock != null) writeLock.lock();
                    Object object = sendReceiveDataListener.sendData();
                    if (object != null) out.writeObject(object);
                    if (writeLock != null && writeLock.isHeldByCurrentThread()) writeLock.unlock();
                    if (object != null) {
                        out.flush();
                        Thread.sleep(15);
                        out.reset();
                    }
                } catch (IOException e) {
                    if (((SocketException) e).getMessage().equals("Socket closed") || ((SocketException) e).getMessage().equals("Connection reset by peer: socket write error") || ((SocketException) e).getMessage().equals("Software caused connection abort: socket write error")) {
                        if (!socket.isClosed()) socket.close();
                        break;
                    } else e.printStackTrace();
                } catch (ConcurrentModificationException e) {
                    System.out.println("Concurrent exception");
                } finally {
                    if (writeLock != null && writeLock.isHeldByCurrentThread()) writeLock.unlock();
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
