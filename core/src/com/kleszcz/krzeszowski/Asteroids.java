package com.kleszcz.krzeszowski;

import com.badlogic.gdx.Game;
import com.kleszcz.krzeszowski.ui.MainMenuScreen;

public class Asteroids extends Game {
	
	@Override
	public void create () {
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize (int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void dispose () {
		super.dispose();
		getScreen().dispose();
	}
}
