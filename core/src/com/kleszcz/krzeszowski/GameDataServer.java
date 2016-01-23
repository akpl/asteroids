package com.kleszcz.krzeszowski;

import com.kleszcz.krzeszowski.game.Asteroid;
import com.kleszcz.krzeszowski.game.Player;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Elimas on 2016-01-23.
 */
public class GameDataServer implements Serializable {
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Player> allPlayers;

    public ArrayList<Asteroid> getAsteroids() {
        return asteroids;
    }

    public void setAsteroids(ArrayList<Asteroid> asteroids) {
        this.asteroids = asteroids;
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(ArrayList<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }
}
