package com.greenpineapple;

import com.badlogic.gdx.Game;
import com.greenpineapple.game.GameScreen;
import com.greenpineapple.menu.MainMenuScreen;

public class GreenPineappleGame extends Game {
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 512;
	
	@Override
	public void create() {
		setScreen(new MainMenuScreen(this));
//		setScreen(new GameScreen(this));
	}

}
