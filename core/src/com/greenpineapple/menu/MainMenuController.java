package com.greenpineapple.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuController {

	public MainMenuController(MainMenuScreen screen, Skin skin) {
		createInvitePlayerListener(screen, skin);
		

	}
	
	private void createInvitePlayerListener(MainMenuScreen screen, Skin skin) {
		screen.buttonInvitePlayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screen.addClientPlayerRow(skin);
			}
		});
	}
}
