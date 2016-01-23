package com.kleszcz.krzeszowski;

import com.kleszcz.krzeszowski.game.Player;

import java.io.Serializable;

/**
 * Created by Elimas on 2016-01-22.
 */
public class GameDataClient implements Serializable {
    private String name = "";
    private Player player;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
