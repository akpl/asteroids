package com.kleszcz.krzeszowski.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kleszcz.krzeszowski.Asteroids;
import com.kleszcz.krzeszowski.GameOptions;
import com.kleszcz.krzeszowski.game.GameScreen;
import com.kleszcz.krzeszowski.multiplayer.Client;

public class JoinGameScreen extends MenuScreen {
    public JoinGameScreen(Asteroids asteroids) {
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
        final Label header = new Label("Dolacz do gry", skin, "header");
        table.add(header).expandX();

        table.row();
        table.add().expandY().fillY();

        table.row().pad(15);
        final Label addressLabel = new Label("IP serwera: ", skin);
        table.add(addressLabel);
        table.row().height(50).width(400);
        final TextField addressTextField = new TextField("127.0.0.1", skin);
        table.add(addressTextField);

        table.row();
        table.add().expandY().fillY();

        table.row().height(50).width(200);
        final TextButton joinGameButton = new TextButton("Start!", skin);
        table.add(joinGameButton).right();

        joinGameButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Client client = new Client(addressTextField.getText(), 1234);
                new Thread(client).start();
                GameOptions gameOptions = GameOptions.newClient(client);
                GameScreen gameScreen = new GameScreen(asteroids, gameOptions);
                client.setSendReceiveDataListener(gameScreen);
                asteroids.setScreen(gameScreen);
            }
        });
    }
}
