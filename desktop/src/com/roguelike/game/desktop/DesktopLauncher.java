package com.roguelike.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.roguelike.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		  config.title = "Game";
		  config.width = 800;
		  config.height = 480;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
