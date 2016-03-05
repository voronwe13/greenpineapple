package com.greenpineapple.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.greenpineapple.game.GPAGame;
import com.greenpineapple.net.chat.ChatModule;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Green Pineapple";
		config.width = 800;
		config.height = 480;
		GPAGame game = new GPAGame();
		game.setScreenDimensions(config.width, config.height);
		new LwjglApplication(game, config);
		//new LwjglApplication(new ChatModule(), config);
	}
}
