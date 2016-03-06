package com.greenpineapple.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.greenpineapple.net.NetworkObject;
import com.greenpineapple.net.NetworkObjectDescription;
import com.greenpineapple.net.NetworkTransmitter;
import com.greenpineapple.net.primitive.NetworkString;

public class ClientPlayerController {

	private String source = "";
	
	private ClientPlayerRow row;
	
	public ClientPlayerController(ClientPlayerRow row) {
		this.row = row;
		
		createIPAddressListener();
	}
	
	public void dispose() {
		row = null;
	}
	
	public String getSource() {
		return source;
	}
	
	public void actOn(NetworkObject networkObject) {
		if (networkObject.getDescription().equals(NetworkObjectDescription.PLAYER_NAME)) {
			row.labelPlayerName.setText(((NetworkString) networkObject).getMessage());
		}
	}
	
	private void createIPAddressListener() {
		row.textIPAddress.addListener(new InputListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				if (character == '\r' || character == '\n') {
					attemptNetworkConnection();
				}
				return false;
			}
		});
	}
	
	private void attemptNetworkConnection() {
		row.labelPlayerName.setText("Connecting...");
		if (!NetworkTransmitter.addClient(row.textIPAddress.getText())) {
			row.labelPlayerName.setText("Unable to connect to client at " + row.textIPAddress.getText());
		} else {
			row.labelPlayerName.setText("Connected!");
			source = row.textIPAddress.getText();
		}
	}
}
