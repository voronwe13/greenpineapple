package com.greenpineapple.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.greenpineapple.net.chat.ChatModule;

public class DesktopLauncher {
	public static void main(String[] args) {

		for (String arg : args) {
			System.out.println(arg);
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ChatModule(), config);
	}
}
