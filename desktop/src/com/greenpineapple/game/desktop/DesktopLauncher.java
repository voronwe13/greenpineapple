package com.greenpineapple.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.greenpineapple.game.GPAGame;
import com.greenpineapple.net.chat.ChatModule;

public class DesktopLauncher {
	public static void main (String[] arg) {
		GPAGame game = new GPAGame();
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Green Pineapple";
		config.width = GPAGame.screenwidth;
		config.height = GPAGame.screenheight;
		new LwjglApplication(game, config);
		//new LwjglApplication(new ChatModule(), config);
	}
}
