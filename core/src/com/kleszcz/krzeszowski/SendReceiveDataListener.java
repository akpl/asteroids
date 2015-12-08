package com.kleszcz.krzeszowski;

/**
 * Created by Elimas on 2015-12-08.
 */
public interface SendReceiveDataListener {
    public String sendData();
    public void onDataReceived(String command, String value);
}
