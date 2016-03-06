package com.greenpineapple;

import com.badlogic.gdx.Game;
import com.greenpineapple.menu.MainMenuScreen;
import com.greenpineapple.net.NetworkReceiver;

public class GreenPineappleGame extends Game {
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 512;
	
	@Override
	public void create() {
		NetworkReceiver.createServer();
		
		setScreen(new MainMenuScreen(this));
	}

}