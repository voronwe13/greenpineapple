package com.greenpineapple.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.greenpineapple.net.NetworkTransmitter;

public class ClientPlayerController {

	public ClientPlayerController(ClientPlayerRow row) {
		
		createIPAddressListener(row);
	}
	
	private void createIPAddressListener(ClientPlayerRow row) {
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
	
	private void attemptNetworkConnection(ClientPlayerRow row) {
		if (!NetworkTransmitter.addClient(row.textIPAddress.getText())) {
			row.labelPlayerName.setText("Unable to connect to client at " + row.textIPAddress.getText());
		} else {
			row.labelPlayerName.setText("Connected!");
		}
	}
}
