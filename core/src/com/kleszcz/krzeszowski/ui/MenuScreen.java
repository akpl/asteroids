package com.kleszcz.krzeszowski.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kleszcz.krzeszowski.Asteroids;
import com.kleszcz.krzeszowski.GameOptions;
import com.kleszcz.krzeszowski.game.AsteroidDesignerScreen;
import com.kleszcz.krzeszowski.game.GameScreen;
import com.kleszcz.krzeszowski.multiplayer.Client;
import com.kleszcz.krzeszowski.multiplayer.Server;

public abstract class MenuScreen implements Screen {
    protected Asteroids asteroids;
    protected Skin skin = new Skin();
    protected Stage stage = new Stage();

    public MenuScreen(Asteroids asteroids) {
        this.asteroids = asteroids;
        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("checkboxOn", new Texture(Gdx.files.internal("check-on.png")));
        skin.add("checkboxOff", new Texture(Gdx.files.internal("check-off.png")));

        // Store the default libgdx font under the name "default".
        skin.add("default", new BitmapFont());
        skin.add("header", new BitmapFont(Gdx.files.internal("Arial32.fnt")));

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        Label.LabelStyle headerTextStyle = new Label.LabelStyle();
        headerTextStyle.font = skin.getFont("header");
        skin.add("header", headerTextStyle);

        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.font = skin.getFont("default");
        checkBoxStyle.checkboxOn = skin.newDrawable("checkboxOn", Color.WHITE);
        checkBoxStyle.checkboxOff = skin.newDrawable("checkboxOff", Color.GRAY);
        skin.add("default", checkBoxStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.FOREST;
        textFieldStyle.background = skin.newDrawable("white", Color.DARK_GRAY);
        textFieldStyle.selection  = skin.newDrawable("white", Color.CHARTREUSE);
        textFieldStyle.cursor     = skin.newDrawable("white", Color.ORANGE);
        skin.add("default", textFieldStyle);

        List.ListStyle listStyle = new List.ListStyle();
        listStyle.background = skin.newDrawable("white", Color.DARK_GRAY);
        listStyle.selection = skin.newDrawable("white", Color.LIGHT_GRAY);
        listStyle.font = skin.getFont("default");
        listStyle.fontColorSelected = Color.WHITE;
        listStyle.fontColorUnselected = Color.GREEN;
        skin.add("default", listStyle);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        stage.dispose();
        skin.dispose();
    }
}
