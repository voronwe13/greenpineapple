package com.greenpineapple.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.greenpineapple.net.NetworkTransmitter;
import com.greenpineapple.player.Players;

public class RemotePlayerRowController {

	public RemotePlayerRowController(RemotePlayerRow row) {
		createIPAddressListener(row);
	}

	private void createIPAddressListener(RemotePlayerRow row) {
		row.textIPAddress.addListener(new InputListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				if (character == '\r' || character == '\n') {
					attemptNetworkConnection(row);
				}
				return false;
			}
		});
	}

	private void attemptNetworkConnection(RemotePlayerRow row) {
		row.labelPlayerName.setText("Connecting...");
		if (!NetworkTransmitter.addClient(row.textIPAddress.getText())) {
			row.labelPlayerName.setText("Unable to connect to client at " + row.textIPAddress.getText());
		} else {
			row.labelPlayerName.setText("Connected!");
			Players.createPlayer(row.textIPAddress.getText());
		}
	}
}
