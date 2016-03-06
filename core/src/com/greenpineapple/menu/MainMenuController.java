package com.greenpineapple.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.greenpineapple.game.GameScreen;

public class MainMenuController {

	public MainMenuController(MainMenuScreen screen, Skin skin, Game game) {
		createInvitePlayerListener(screen, skin);
		
		createPlayButtonListener(screen, game);

	}
	
	private void createInvitePlayerListener(MainMenuScreen screen, Skin skin) {
		screen.buttonInvitePlayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screen.addClientPlayerRow(skin);
			}
		});
	}
	
	private void createPlayButtonListener(MainMenuScreen screen, Game game) {
		screen.buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game));
				screen.dispose();
			}
		});
	}
}
