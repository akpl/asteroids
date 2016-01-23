package com.kleszcz.krzeszowski.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.io.Serializable;

/**
 * Created by Elimas on 2016-01-23.
 */
public class ActorData implements Serializable {
    private String name;
    private float x;
    private float y;
    private float width;
    private float height;
    private float originX;
    private float originY;
    private float scaleX;
    private float scaleY;
    private float rotation;
    private float colorR, colorG, colorB, colorA;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public float getOriginY() {
        return originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getColorR() {
        return colorR;
    }

    public void setColorR(float colorR) {
        this.colorR = colorR;
    }

    public float getColorG() {
        return colorG;
    }

    public void setColorG(float colorG) {
        this.colorG = colorG;
    }

    public float getColorB() {
        return colorB;
    }

    public void setColorB(float colorB) {
        this.colorB = colorB;
    }

    public float getColorA() {
        return colorA;
    }

    public void setColorA(float colorA) {
        this.colorA = colorA;
    }

    public ActorData() {

    }

    public ActorData(Actor actor) {
        setName(actor.getName());
        setX(actor.getX());
        setY(actor.getY());
    }

    public void copyToActor(Actor actor) {
        actor.setName(getName());
        actor.setX(getX());
        actor.setY(getY());
    }
}
