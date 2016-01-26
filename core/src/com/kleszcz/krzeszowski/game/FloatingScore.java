package com.kleszcz.krzeszowski.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import java.io.Serializable;

/**
 * Created by Elimas on 2015-12-04.
 */
public class FloatingScore implements Serializable {
    private transient BitmapFont font = new BitmapFont(Gdx.files.internal("Arial15.fnt"));
    private transient GlyphLayout textGlyph;
    private float x;
    private float y;
    private String text;
    private int timeLeft;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        if (font != null) textGlyph = new GlyphLayout(font, text);
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public FloatingScore() {
    }

    public FloatingScore(String text, float x, float y, int timeLeft) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.timeLeft = timeLeft;
        textGlyph = new GlyphLayout(font, text);
    }

    public void update(float delta) {
        if (timeLeft > 0) {
            timeLeft -= delta * 1000;
        } else {
            timeLeft = 0;
        }
    }

    public void draw(Batch batch) {
        font.draw(batch, getText(), getX() - textGlyph.width * 0.5f, getY() - textGlyph.height * 0.5f);
    }

    public void loadContent() {
        font = new BitmapFont(Gdx.files.internal("Arial15.fnt"));
        textGlyph = new GlyphLayout(font, text);
    }
}
