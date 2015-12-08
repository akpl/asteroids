package com.kleszcz.krzeszowski;

import com.badlogic.gdx.Game;
import com.kleszcz.krzeszowski.ui.MenuScreen;

public class Asteroids extends Game {
	
	@Override
	public void create () {
		setScreen(new MenuScreen(this));
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
