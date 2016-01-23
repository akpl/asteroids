package com.kleszcz.krzeszowski.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.kleszcz.krzeszowski.Utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Elimas on 2015-11-21.
 */
public class Asteroid extends Polygon implements Serializable {
    private float direction;
    private float speed;
    private float rotationSpeed;

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public Asteroid() {

    }

    public Asteroid(Asteroid other) {
        this.setVertices(other.getVertices());
        this.setOrigin(other.getOriginX(), other.getOriginY());
        this.setRotation(other.getRotation());
        this.setScale(other.getScaleX(), other.getScaleY());
        this.setPosition(other.getX(), other.getY());
        this.setDirection(other.getDirection());
        this.setRotationSpeed(other.getRotationSpeed());
        this.setSpeed(other.getSpeed());
    }

    public void update() {
        translate(speed * (float) Math.cos(Math.toRadians(getDirection() + 90)), speed * (float) Math.sin(Math.toRadians(getDirection() + 90)));
        setRotation(getRotation() + rotationSpeed);
        setRotation(Utils.normalizeDegrees(getRotation()));
        setDirection(Utils.normalizeDegrees(getDirection()));
    }

    public int calcScore() {
        return (int)(((getScaleX() + getScaleY() + speed * 5f) / 250f) * 10f);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeFloat(getX());
        out.writeFloat(getY());
        out.writeFloat(getOriginX());
        out.writeFloat(getOriginY());
        out.writeFloat(getScaleX());
        out.writeFloat(getScaleY());
        out.writeFloat(getRotation());
        out.writeObject(getVertices());
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setPosition(in.readFloat(), in.readFloat());
        setOrigin(in.readFloat(), in.readFloat());
        setScale(in.readFloat(), in.readFloat());
        setRotation(in.readFloat());
        setVertices((float[]) in.readObject());
    }
}
