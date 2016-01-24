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
    private String playerName;

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

    public String getPlayerName() {
        return playerName;
    }

    private GameOptions() {}

    public static GameOptions newServer(Server server, String playerName) {
        GameOptions g = new GameOptions();
        g.isServer = true;
        g.server = server;
        g.playerName = playerName;
        return g;
    }

    public static GameOptions newClient(Client client, String playerName) {
        GameOptions g = new GameOptions();
        g.isServer = false;
        g.client = client;
        g.playerName = playerName;
        return g;
    }
}
