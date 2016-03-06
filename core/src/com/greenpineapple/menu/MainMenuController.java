package com.greenpineapple.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.greenpineapple.game.GameScreen;
import com.greenpineapple.net.NetworkObject;
import com.greenpineapple.net.NetworkObjectDescription;
import com.greenpineapple.net.NetworkReceiver;
import com.greenpineapple.net.NetworkTransmitter;
import com.greenpineapple.net.primitive.NetworkBoolean;
import com.greenpineapple.net.primitive.NetworkString;

public class MainMenuController {

	private NetworkString playerName;
	private NetworkBoolean playerReady;
	private NetworkBoolean playerGuards;
	private NetworkBoolean playerThieves;

	private List<ClientPlayerController> controllers = new ArrayList<>();

	public MainMenuController(MainMenuScreen screen, Skin skin, Game game, String ipAddress) {
		registerNetworkObjects(screen, ipAddress);

		createInvitePlayerListener(screen, skin);
		createPlayButtonListener(screen, game);
		createPlayerNameListener(screen);
		createPlayerReadyListener(screen);
		createPlayerGuardTeamListener(screen);
		createPlayerThiefTeamListener(screen);
	}

	public void dispose() {
		playerName.dispose();
		controllers.forEach(controller -> controller.dispose());
		controllers.clear();
	}

	public void checkNetwork() {
		List<NetworkObject> networkObjects = NetworkReceiver.retrieveUpdates();

		for (NetworkObject networkObject : networkObjects) {
			Optional<ClientPlayerController> matchingController = controllers.parallelStream()
					.filter(controller -> controller.getSource().equals(networkObject.getSource())).findAny();
			if (matchingController.isPresent()) {
				matchingController.get().actOn(networkObject);
			}
		}
	}

	private void registerNetworkObjects(MainMenuScreen screen, String ipAddress) {
		playerName = new NetworkString(ipAddress, NetworkObjectDescription.PLAYER_NAME);
		playerName.setMessage(screen.textPlayerName.getText());
		NetworkTransmitter.register(playerName);
		
		playerReady = new NetworkBoolean(ipAddress, NetworkObjectDescription.PLAYER_READY);
		playerReady.setChecked(screen.checkReady.isChecked());
		NetworkTransmitter.register(playerReady);
		
		playerGuards = new NetworkBoolean(ipAddress, NetworkObjectDescription.PLAYER_GUARD_TEAM);
		playerGuards.setChecked(screen.checkGuards.isChecked());
		NetworkTransmitter.register(playerGuards);
		
		playerThieves = new NetworkBoolean(ipAddress, NetworkObjectDescription.PLAYER_THIEF_TEAM);
		playerThieves.setChecked(screen.checkThieves.isChecked());
		NetworkTransmitter.register(playerThieves);
	}

	private void createInvitePlayerListener(MainMenuScreen screen, Skin skin) {
		screen.buttonInvitePlayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				controllers.add(screen.addClientPlayerRow(skin));
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

	private void createPlayerNameListener(MainMenuScreen screen) {
		screen.textPlayerName.addListener(new InputListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				if (character == '\r' || character == '\n') {
					playerName.setMessage(screen.textPlayerName.getText());
				}
				return false;
			}
		});
	}
	
	private void createPlayerReadyListener(MainMenuScreen screen) {
		screen.checkReady.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				playerReady.setChecked(screen.checkReady.isChecked());
			}
		});
	}
	
	private void createPlayerGuardTeamListener(MainMenuScreen screen) {
		screen.checkGuards.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (screen.checkGuards.isChecked()) {
					enableCheckReady(screen);
				}
				playerGuards.setChecked(screen.checkGuards.isChecked());
			}
		});
	}
	
	private void createPlayerThiefTeamListener(MainMenuScreen screen) {
		screen.checkThieves.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (screen.checkThieves.isChecked()) {
					enableCheckReady(screen);
				}
				playerThieves.setChecked(screen.checkThieves.isChecked());
			}
		});
	}

	private void enableCheckReady(MainMenuScreen screen) {
		screen.checkReady.setDisabled(false);
	}
}
