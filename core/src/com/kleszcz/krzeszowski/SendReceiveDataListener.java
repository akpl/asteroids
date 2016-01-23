package com.kleszcz.krzeszowski;

/**
 * Created by Elimas on 2015-12-08.
 */
public interface SendReceiveDataListener {
    Object sendData();
    void onDataReceived(Object object);
}
