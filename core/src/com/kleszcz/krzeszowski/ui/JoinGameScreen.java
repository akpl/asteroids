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
import com.kleszcz.krzeszowski.SendReceiveDataListener;
import com.kleszcz.krzeszowski.game.GameScreen;
import com.kleszcz.krzeszowski.multiplayer.Client;

public class JoinGameScreen extends MenuScreen {
    private String playerNick;
    public JoinGameScreen(Asteroids asteroids, String playerNick) {
        super(asteroids);
        this.playerNick = playerNick;
    }
    private boolean gameStarted = false;
    Client client;


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
                client = new Client(addressTextField.getText(), 1234);
                client.setSendReceiveDataListener(new SendReceiveDataListener() {
                    private boolean nickSent = false;

                    @Override
                    public Object sendData() {
                        if(nickSent)
                            return null;
                        nickSent = true;
                        return playerNick;
                    }

                    @Override
                    public void onDataReceived(Object object) {
                        gameStarted = true;
                    }
                });
                new Thread(client).start();
                header.setText("Oczekiwanie na rozpoczecie gry...");
                addressTextField.setDisabled(true);
                joinGameButton.setText("Czekaj...");
            }
        });
    }

    @Override
    public void render (float delta) {
        super.render(delta);
        if(gameStarted) {
            GameOptions gameOptions = GameOptions.newClient(client, playerNick);
            GameScreen gameScreen = new GameScreen(asteroids, gameOptions);
            new Thread(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                client.setSendReceiveDataListener(gameScreen);
            }).start();
            asteroids.setScreen(gameScreen);
        }
    }
}
