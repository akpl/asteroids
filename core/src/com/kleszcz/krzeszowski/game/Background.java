package com.kleszcz.krzeszowski.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Elimas on 2015-11-20.
 */
public class Background extends Actor {
    Texture texture = new Texture(Gdx.files.internal("background.jpg"));

    public Background() {
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        setScale(0.9f);
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), texture.getWidth() * getScaleX(),
                texture.getHeight() * getScaleY(), getScaleX(), getScaleY(), getRotation(), 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
    }
}