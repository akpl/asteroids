package com.kleszcz.krzeszowski.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.kleszcz.krzeszowski.Utils;

/**
 * Created by Elimas on 2015-11-21.
 */
public class Shoot extends Actor {
    private Texture texture = new Texture(Gdx.files.internal("pixel.png"));
    private Player owner;
    private float speed = 10;

    public Shoot(Player owner) {
        this.owner = owner;
        setScaleX(2);
        setScaleY(5);
        setWidth(texture.getWidth() * getScaleX());
        setHeight(texture.getHeight() * getScaleY());
        setColor(0.93f, 0.93f, 0, 1);
        setRotation(owner.getRotation());
        setOrigin(Align.bottom);
        setX(owner.getX() + 20 * (float) Math.cos(Math.toRadians(getRotation() + 90)));
        setY(owner.getY() + 20 * (float) Math.sin(Math.toRadians(getRotation() + 90)));
    }

    @Override
    public void draw(Batch batch, float alpha){
        Color previousColor = batch.getColor();
        batch.setColor(getColor());
        batch.draw(texture, getX() - getOriginX(), getY() - getOriginY(), getOriginX(), getOriginY(), texture.getWidth() * getScaleX(),
                texture.getHeight() * getScaleY(), getScaleX(), getScaleY(), getRotation(), 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
        batch.setColor(previousColor);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setX(getX() + speed * (float) Math.cos(Math.toRadians(getRotation() + 90)));
        setY(getY() + speed * (float) Math.sin(Math.toRadians(getRotation() + 90)));
    }
}
