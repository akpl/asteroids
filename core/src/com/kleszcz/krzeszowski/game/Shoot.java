package com.kleszcz.krzeszowski.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.kleszcz.krzeszowski.Utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Elimas on 2015-11-21.
 */
public class Shoot extends Actor implements Serializable {
    private transient Texture texture = new Texture(Gdx.files.internal("pixel.png"));
    private transient Player owner;
    private int clientId;
    private float speed = 6;

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
        clientId = owner.getClientId();
    }

    public int getClientId() {
        return clientId;
    }

    public Shoot() {

    }

    public Shoot(Player owner) {
        this.owner = owner;
        clientId = owner.getClientId();
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

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        //out.writeUTF(getName());
        out.writeFloat(getX());
        out.writeFloat(getY());
        out.writeFloat(getWidth());
        out.writeFloat(getHeight());
        out.writeFloat(getOriginX());
        out.writeFloat(getOriginY());
        out.writeFloat(getScaleX());
        out.writeFloat(getScaleY());
        out.writeFloat(getRotation());
        out.writeFloat(getColor().r);
        out.writeFloat(getColor().g);
        out.writeFloat(getColor().b);
        out.writeFloat(getColor().a);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        //setName(in.readUTF());
        setX(in.readFloat());
        setY(in.readFloat());
        setWidth(in.readFloat());
        setHeight(in.readFloat());
        setOriginX(in.readFloat());
        setOriginY(in.readFloat());
        setScaleX(in.readFloat());
        setScaleY(in.readFloat());
        setRotation(in.readFloat());
        Color color = new Color();
        color.r = in.readFloat();
        color.g = in.readFloat();
        color.b = in.readFloat();
        color.a = in.readFloat();
        setColor(color);
    }

    public void loadLibgdxContent() {
        texture = new Texture(Gdx.files.internal("pixel.png"));
    }
}
