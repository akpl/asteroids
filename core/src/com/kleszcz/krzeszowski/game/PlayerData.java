package com.kleszcz.krzeszowski.game;

import java.io.Serializable;

public class PlayerData extends ActorData implements Serializable {
    private int lives;
    private int score;
    private float speed;
    private float rotateSpeed;
    private int fireInterval;
    private int fireTimer;
    private boolean isShieldEnabled;
    private int shieldTimer;
    private boolean isFlyingForward;
    private boolean isRotatingLeft;
    private boolean isRotatingRight;
    private boolean isFiring;

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getRotateSpeed() {
        return rotateSpeed;
    }

    public void setRotateSpeed(float rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
    }

    public int getFireInterval() {
        return fireInterval;
    }

    public void setFireInterval(int fireInterval) {
        this.fireInterval = fireInterval;
    }

    public int getFireTimer() {
        return fireTimer;
    }

    public void setFireTimer(int fireTimer) {
        this.fireTimer = fireTimer;
    }

    public boolean isShieldEnabled() {
        return isShieldEnabled;
    }

    public void setShieldEnabled(boolean shieldEnabled) {
        isShieldEnabled = shieldEnabled;
    }

    public int getShieldTimer() {
        return shieldTimer;
    }

    public void setShieldTimer(int shieldTimer) {
        this.shieldTimer = shieldTimer;
    }

    public boolean isFlyingForward() {
        return isFlyingForward;
    }

    public void setFlyingForward(boolean flyingForward) {
        isFlyingForward = flyingForward;
    }

    public boolean isRotatingLeft() {
        return isRotatingLeft;
    }

    public void setRotatingLeft(boolean rotatingLeft) {
        isRotatingLeft = rotatingLeft;
    }

    public boolean isRotatingRight() {
        return isRotatingRight;
    }

    public void setRotatingRight(boolean rotatingRight) {
        isRotatingRight = rotatingRight;
    }

    public boolean isFiring() {
        return isFiring;
    }

    public void setFiring(boolean firing) {
        isFiring = firing;
    }

    public PlayerData() {
        super();
    }

    public PlayerData(Player player) {
        super(player);
        lives = player.getLives();
        score = player.getScore();

    }

    public void copyToPlayer(Player player) {
        super.copyToActor(player);

    }
}
