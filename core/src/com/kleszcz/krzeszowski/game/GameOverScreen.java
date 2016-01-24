package com.kleszcz.krzeszowski.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kleszcz.krzeszowski.Asteroids;
import com.kleszcz.krzeszowski.ui.MainMenuScreen;
import com.kleszcz.krzeszowski.ui.MenuScreen;

public class GameOverScreen extends MenuScreen implements Screen {
    private GameScreen gameScreen;

    public GameOverScreen(Asteroids asteroids, GameScreen gameScreen) {
        super(asteroids);
        this.gameScreen = gameScreen;
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.pad(30);
        stage.addActor(table);

        table.row().height(100).pad(15);
        final Label header = new Label("Game Over", skin, "header");
        table.add(header).expandX();

        table.row();

        table.row().width(200).height(50).pad(15);
        final TextButton returnMainMenuButton = new TextButton("Wyjscie do menu glownego", skin);
        table.add(returnMainMenuButton);

        table.row().width(200).height(50).pad(15);
        final TextButton quitGameButton = new TextButton("Wyjscie z gry", skin);
        table.add(quitGameButton);

        returnMainMenuButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.disconnect();
                gameScreen = null;
                asteroids.setScreen(new MainMenuScreen(asteroids));
            }
        });

        quitGameButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.disconnect();
                gameScreen = null;
                Gdx.app.exit();
            }
        });
    }
}
