package com.greenpineapple.menu;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.greenpineapple.player.PlayerController;
import com.greenpineapple.player.Players;

public class RemotePlayerRow extends HorizontalGroup {

	private CheckBox checkReady;
	Label labelPlayerName; // Also used for status.
	TextField textIPAddress;
	private CheckBox checkGuards;
	private CheckBox checkThieves;

	public RemotePlayerRow(Skin skin) {
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

		new RemotePlayerRowController(this);
	}

	public void render() {
		if (Players.hasPlayer(textIPAddress.getText())) {
			PlayerController player = Players.getPlayer(textIPAddress.getText());
			labelPlayerName.setText(player.getPlayerName());
			checkReady.setChecked(player.isPlayerReady());
			checkGuards.setChecked(player.isPlayerGuardTeam());
			checkThieves.setChecked(player.isPlayerThiefTeam());
		}
	}
}
