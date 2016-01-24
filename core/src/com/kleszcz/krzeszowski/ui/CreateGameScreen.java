package com.kleszcz.krzeszowski.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kleszcz.krzeszowski.Asteroids;
import com.kleszcz.krzeszowski.GameOptions;
import com.kleszcz.krzeszowski.game.GameScreen;
import com.kleszcz.krzeszowski.multiplayer.Server;

public class CreateGameScreen extends MenuScreen {
    public CreateGameScreen(Asteroids asteroids) {
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
        final Label header = new Label("Utworz gre", skin, "header");
        table.add(header).expandX();

        table.row().pad(30);


        Table nestedTable = new Table();
        final Label playersLabel = new Label("Gracze: ", skin);
        nestedTable.add(playersLabel);
        final Label logLabel = new Label("Log: ", skin);
        nestedTable.add(logLabel);
        nestedTable.row();
        final List playersList = new List(skin);
        playersList.setItems(new String[] {"Mario", "Pacman", "Zelda"});
        nestedTable.add(playersList).width(200).minHeight(300).pad(15);

        final TextArea logTextArea = new TextArea("Uruchamianie serwera\nSerwer oczekuje na polaczenia...", skin);
        logTextArea.setDisabled(true);
        nestedTable.add(logTextArea).width(400).fillY().pad(15);

        table.add(nestedTable).expandY().fillY();

        table.row().height(50).width(200);
        final TextButton startGameButton = new TextButton("Start!", skin);
        table.add(startGameButton).right();

        startGameButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Server server = new Server(1234);
                new Thread(server).start();
                GameOptions gameOptions = GameOptions.newServer(server, "GraczS");
                GameScreen gameScreen = new GameScreen(asteroids, gameOptions);
                server.setSendReceiveDataListener(gameScreen);
                asteroids.setScreen(gameScreen);
            }
        });
    }
}
