package com.kleszcz.krzeszowski.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.kleszcz.krzeszowski.Globals;
import com.kleszcz.krzeszowski.Utils;

/**
 * Created by Elimas on 2015-11-20.
 */
public class Player extends Actor {
    private Texture texture = new Texture(Gdx.files.internal("player2.png"));
    private Texture textureCircle = new Texture(Gdx.files.internal("circle.png"));
    private BitmapFont fontName = new BitmapFont(Gdx.files.internal("Arial15.fnt"));
    private Polygon polygon = new Polygon(new float[] { 0.52f, 0.8622223f, 0.33777776f, 0.40888888f, 0.057777777f, 0.062222224f, 0.52444446f, 0.25777778f, 1.0f, 0.04888889f, 0.72444445f, 0.3911111f, 0.52f, 0.8622223f });
    private GlyphLayout nameGlyph;
    private int lives = 3;
    private int score = 0;
    private float speed = 8;
    private float rotateSpeed = 3;
    private int fireInterval = 20;
    private int fireTimer = 0;
    private boolean isShieldEnabled = false;
    private int shieldTimer = 0;
    private boolean isFlyingForward = false;
    private boolean isRotatingLeft = false;
    private boolean isRotatingRight = false;
    private boolean isFiring = false;

    @Override
    public void setName(String name) {
        super.setName(name);
        nameGlyph = new GlyphLayout(fontName, name);
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        polygon.setPosition(getX(), getY());
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        polygon.setPosition(getX(), getY());
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        polygon.setPosition(getX(), getY());
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        super.setPosition(x, y, alignment);
        polygon.setPosition(getX(), getY());
    }

    @Override
    public void setScaleX(float scaleX) {
        super.setScaleX(scaleX);
        polygon.setScale(getWidth(), getHeight());
    }

    @Override
    public void setScaleY(float scaleY) {
        super.setScaleY(scaleY);
        polygon.setScale(getWidth(), getHeight());
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        polygon.setScale(getWidth(), getHeight());
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        super.setScale(scaleX, scaleY);
        polygon.setScale(getWidth(), getHeight());
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        polygon.setRotation(getRotation());
    }

    @Override
    public void rotateBy(float amountInDegrees) {
        super.rotateBy(amountInDegrees);
        polygon.setRotation(getRotation());
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        polygon.setScale(getWidth(), getHeight());
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        polygon.setScale(getWidth(), getHeight());
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

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

    public Player() {
        setBounds(getX(), getY(), texture.getWidth(), texture.getHeight());
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        setOrigin(Align.center);
        polygon.setOrigin(0.5f, 0.5f);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(texture, getX() - getOriginX(), getY() - getOriginY(), getOriginX(), getOriginY(), texture.getWidth() * getScaleX(),
                texture.getHeight() * getScaleY(), getScaleX(), getScaleY(), getRotation(), 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
        if (isShieldEnabled()) batch.draw(textureCircle, getX() - textureCircle.getWidth() * 0.5f, getY() - textureCircle.getHeight() * 0.5f);
    }

    public void drawName(Batch batch) {
        fontName.draw(batch, getName(), getX() - nameGlyph.width * 0.5f, getY() - getHeight() * 0.5f - 15);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isFlyingForward) {
            float x = getX() + speed * (float) Math.cos(Math.toRadians(getRotation() + 90));
            float y = getY() + speed * (float) Math.sin(Math.toRadians(getRotation() + 90));
            setX(Utils.clipToRange(x, Globals.MAP_BOUNDS.x + getOriginX() - 5, Globals.MAP_BOUNDS.x + Globals.MAP_BOUNDS.width - getOriginX() + 5));
            setY(Utils.clipToRange(y, Globals.MAP_BOUNDS.y + getOriginY() - 5, Globals.MAP_BOUNDS.y + Globals.MAP_BOUNDS.height - getOriginY() + 5));
        }
        if (isRotatingLeft) rotateBy(rotateSpeed);
        if (isRotatingRight) rotateBy(-rotateSpeed);
        if (fireTimer > 0) fireTimer--;
        if (isShieldEnabled) {
            if (shieldTimer > 0) {
                shieldTimer -= delta * 1000;
            } else {
                isShieldEnabled = false;
                shieldTimer = 0;
            }
        }
        setRotation(Utils.normalizeDegrees(getRotation()));
    }

    public boolean fire() {
        if (fireTimer == 0) {
            fireTimer = fireInterval;
            return true;
        } else return false;
    }

    public boolean canFire() {
        if (fireTimer == 0) return true; else return false;
    }
}
