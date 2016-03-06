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
import com.greenpineapple.net.primitive.NetworkString;

public class MainMenuController {

	private final NetworkString playerName;
	private List<ClientPlayerController> controllers = new ArrayList<>();

	public MainMenuController(MainMenuScreen screen, Skin skin, Game game, String ipAddress) {
		playerName = new NetworkString(ipAddress, NetworkObjectDescription.PLAYER_NAME);
		playerName.setMessage(screen.textPlayerName.getText());
		NetworkTransmitter.register(playerName);

		createInvitePlayerListener(screen, skin);
		createPlayButtonListener(screen, game);
		createPlayerNameListener(screen);
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
}
