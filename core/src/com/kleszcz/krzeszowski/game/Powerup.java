package com.kleszcz.krzeszowski.game;

import java.io.Serializable;

/**
 * Created by Elimas on 2016-01-26.
 */
public class Powerup implements Serializable {
    private PowerupType type;
    private int id;
    private float x;
    private float y;
    private int leftTime = 0;
    private int startTime = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PowerupType getType() {
        return type;
    }

    public void setType(PowerupType type) {
        this.type = type;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(int leftTime) {
        this.leftTime = leftTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public Powerup() {
    }

    public Powerup(int id, PowerupType type, float x, float y, int leftTime) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.leftTime = leftTime;
        this.startTime = leftTime;
    }
}
