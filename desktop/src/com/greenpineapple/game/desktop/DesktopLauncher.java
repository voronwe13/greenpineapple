package com.greenpineapple.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.greenpineapple.GreenPineappleGame;
import com.greenpineapple.net.chat.ChatModule;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Green Pineapple";
		config.width = GreenPineappleGame.SCREEN_WIDTH;
		config.height = GreenPineappleGame.SCREEN_HEIGHT;
//		new LwjglApplication(new ChatModule(), config);
//		new LwjglApplication(new OriginalChatModule(), config);
		new LwjglApplication(new GreenPineappleGame(), config);
	}
}
