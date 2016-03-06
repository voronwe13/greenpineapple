package com.greenpineapple.menu;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class ClientPlayerRow extends HorizontalGroup {

	private ClientPlayerController controller;
	CheckBox checkReady;
	Label labelPlayerName; // Also used for status.
	TextField textIPAddress;
	CheckBox checkGuards;
	CheckBox checkThieves;

	public ClientPlayerRow(Skin skin) {
		space(MainMenuScreen.PAD * 5);
		pad(MainMenuScreen.PAD);
		fill();

		checkReady = new CheckBox(" Ready?", skin);
		checkReady.setDisabled(true);
		addActor(checkReady);
		labelPlayerName = new Label("Enter IP Address", skin);
		addActor(labelPlayerName);
		textIPAddress = new TextField("192.168.1.X", skin);
		addActor(textIPAddress);

		checkGuards = new CheckBox(" Guards", skin);
		checkGuards.setDisabled(true);
		checkThieves = new CheckBox(" Thieves", skin);
		checkThieves.setDisabled(true);

		addActor(checkGuards);
		addActor(checkThieves);

		controller = new ClientPlayerController(this);
	}

	public void dispose() {
		controller = null;
	}

	ClientPlayerController getController() {
		return controller;
	}
}
