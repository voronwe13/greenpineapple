package com.greenpineapple.menu;

import java.util.Objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.greenpineapple.game.GameScreen;
import com.greenpineapple.player.Players;

public class MainMenuController {

	public MainMenuController(MainMenuScreen screen, Skin skin, Game game, String ipAddress) {
		Objects.requireNonNull(screen);
		Objects.requireNonNull(skin);
		Objects.requireNonNull(game);
		Objects.requireNonNull(ipAddress);

		createInvitePlayerListener(screen, skin);
		createPlayButtonListener(screen, game);
		createPlayerNameListener(screen, ipAddress);
		createPlayerReadyListener(screen, ipAddress);
		createPlayerGuardTeamListener(screen, ipAddress);
		createPlayerThiefTeamListener(screen, ipAddress);
	}

	private void createInvitePlayerListener(MainMenuScreen screen, Skin skin) {
		screen.buttonInvitePlayer.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				screen.addRemotePlayerRow(skin);
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

	private void createPlayerNameListener(MainMenuScreen screen, String ipAddress) {
		screen.textPlayerName.addListener(new InputListener() {
			@Override
			public boolean keyTyped(InputEvent event, char character) {
				if (character == '\r' || character == '\n') {
					Players.getPlayer(ipAddress).setPlayerName(screen.textPlayerName.getText());
				}
				return false;
			}
		});
	}

	private void createPlayerReadyListener(MainMenuScreen screen, String ipAddress) {
		screen.checkReady.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Players.getPlayer(ipAddress).setPlayerReady(screen.checkReady.isChecked());
			}
		});
	}

	private void createPlayerGuardTeamListener(MainMenuScreen screen, String ipAddress) {
		screen.checkGuards.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (screen.checkGuards.isChecked()) {
					enableCheckReady(screen);
				}
				Players.getPlayer(ipAddress).setPlayerGuardTeam(screen.checkGuards.isChecked());
				Players.getPlayer(ipAddress).setPlayerThiefTeam(screen.checkThieves.isChecked());
			}
		});
	}

	private void createPlayerThiefTeamListener(MainMenuScreen screen, String ipAddress) {
		screen.checkThieves.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (screen.checkThieves.isChecked()) {
					enableCheckReady(screen);
				}
				Players.getPlayer(ipAddress).setPlayerGuardTeam(screen.checkGuards.isChecked());
				Players.getPlayer(ipAddress).setPlayerThiefTeam(screen.checkThieves.isChecked());
			}
		});
	}

	private void enableCheckReady(MainMenuScreen screen) {
		screen.checkReady.setDisabled(false);
	}
}
