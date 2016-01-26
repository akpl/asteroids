package com.kleszcz.krzeszowski;

import com.kleszcz.krzeszowski.game.Asteroid;
import com.kleszcz.krzeszowski.game.Player;
import com.kleszcz.krzeszowski.game.Powerup;
import com.kleszcz.krzeszowski.game.Shoot;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Elimas on 2016-01-23.
 */
public class GameDataServer implements Serializable {
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Player> allPlayers;
    private ArrayList<Shoot> shoots;
    private ArrayList<Powerup> powerups;

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

    public ArrayList<Shoot> getShoots() {
        return shoots;
    }

    public void setShoots(ArrayList<Shoot> shoots) {
        this.shoots = shoots;
    }

    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }

    public void setPowerups(ArrayList<Powerup> powerups) {
        this.powerups = powerups;
    }
}
