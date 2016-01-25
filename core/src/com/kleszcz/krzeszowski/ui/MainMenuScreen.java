package com.kleszcz.krzeszowski.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kleszcz.krzeszowski.Asteroids;

public class MainMenuScreen extends MenuScreen implements Screen {
    public MainMenuScreen(Asteroids asteroids) {
        super(asteroids);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.pad(30);
        stage.addActor(table);

        table.row().height(100).pad(15);
        final Label header = new Label("Asteroidy Multiplayer", skin, "header");
        table.add(header).expandX();

        table.row();
        table.add().expandY().fillY();

        table.row().width(200).height(50).pad(15);
        final TextButton createGameButton = new TextButton("Utworz gre", skin);
        table.add(createGameButton);

        table.row().width(200).height(50).pad(15);
        final TextButton joinGameButton = new TextButton("Dolacz do gry", skin);
        table.add(joinGameButton);

        table.row().width(200).height(50).pad(15);
        final TextButton quitGameButton = new TextButton("Wyjscie", skin);
        table.add(quitGameButton);

        table.row();
        table.add().expandY().fillY();

        table.row().height(50).pad(15).fillX();
        Table horizontalTable = new Table();
        final CheckBox fullScreenCheckBox = new CheckBox("  Pelny ekran", skin);
        horizontalTable.add(fullScreenCheckBox);

        horizontalTable.add().fillX().expandX();

        final Label nickLabel = new Label("Nick:   ", skin);
        horizontalTable.add(nickLabel);

        final TextField nickTextField = new TextField("Gracz1", skin);
        horizontalTable.add(nickTextField);

        table.add(horizontalTable).fill();


        createGameButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                asteroids.setScreen(new CreateGameScreen(asteroids, nickTextField.getText()));
            }
        });

        joinGameButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                asteroids.setScreen(new JoinGameScreen(asteroids, nickTextField.getText()));
            }
        });

        quitGameButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        fullScreenCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.graphics.setDisplayMode(1280, 720, fullScreenCheckBox.isChecked());
            }
        });
    }
}
