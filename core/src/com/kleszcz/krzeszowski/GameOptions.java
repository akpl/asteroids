package com.kleszcz.krzeszowski;

import com.kleszcz.krzeszowski.multiplayer.Client;
import com.kleszcz.krzeszowski.multiplayer.Server;

/**
 * Created by Elimas on 2015-12-08.
 */
public class GameOptions {
    private boolean isServer;
    private Server server;
    private Client client;

    public boolean isServer() {
        return isServer;
    }

    public boolean isClient() {
        return !isServer;
    }

    public Server getServer() {
        return server;
    }

    public Client getClient() {
        return client;
    }

    private GameOptions() {}

    public static GameOptions newServer(Server server) {
        GameOptions g = new GameOptions();
        g.isServer = true;
        g.server = server;
        return g;
    }

    public static GameOptions newClient(Client client) {
        GameOptions g = new GameOptions();
        g.isServer = false;
        g.client = client;
        return g;
    }
}
