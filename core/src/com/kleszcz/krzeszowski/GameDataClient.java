package com.kleszcz.krzeszowski;

import com.kleszcz.krzeszowski.game.Player;
import com.kleszcz.krzeszowski.game.Powerup;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Elimas on 2016-01-22.
 */
public class GameDataClient implements Serializable {
    private String name = "";
    private Player player;
    private ArrayList<Integer> takenPowerups;

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

    public ArrayList<Integer> getTakenPowerups() {
        return takenPowerups;
    }

    public void setTakenPowerups(ArrayList<Integer> takenPowerups) {
        this.takenPowerups = takenPowerups;
    }
}
