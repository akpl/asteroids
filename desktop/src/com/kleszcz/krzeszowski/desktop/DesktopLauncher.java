package com.kleszcz.krzeszowski.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kleszcz.krzeszowski.Asteroids;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Asteroids Multiplayer";
		config.width = 1280;
		config.height = 720;
		config.samples = 2;
		new LwjglApplication(new Asteroids(), config);
	}
}
