package com.greenpineapple.menu;

import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

public class ClientPlayerRow extends HorizontalGroup {

	@SuppressWarnings("unused")
	private ClientPlayerController controller;
	
	public ClientPlayerRow(Skin skin) {	
		space(MainMenuScreen.PAD * 5);
		pad(MainMenuScreen.PAD);
		fill();

		CheckBox checkReady = new CheckBox("Ready?", skin);
		checkReady.setDisabled(true);
		addActor(checkReady);
		addActor(new Label("Enter IP Address", skin));
		addActor(new TextArea("192.168.1.X", skin));

		CheckBox checkGuards = new CheckBox("Guards", skin);
		checkGuards.setDisabled(true);
		CheckBox checkThieves = new CheckBox("Thieves", skin);
		checkThieves.setDisabled(true);

		addActor(checkGuards);
		addActor(checkThieves);

		ButtonGroup<CheckBox> buttonGroupTeam = new ButtonGroup<>(checkGuards, checkThieves);
		buttonGroupTeam.setMaxCheckCount(1);
		
		controller = new ClientPlayerController(this);
	}
	
	public void dispose() {
		controller = null;
	}
}
