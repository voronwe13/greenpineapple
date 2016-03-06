package com.greenpineapple.menu;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.greenpineapple.GreenPineappleGame;

public class MainMenuScreen implements Screen {
	public static final float PAD = 5f;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;

	private VerticalGroup rows;
	private HorizontalGroup invitePlayerRow;
	private List<ClientPlayerRow> clientPlayerRows = new ArrayList<>();
	
	@SuppressWarnings("unused")
	private MainMenuController mainMenuController;
	TextButton buttonInvitePlayer;
	TextButton buttonPlay;

	public MainMenuScreen(Game game) {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

		rows = new VerticalGroup().space(PAD).pad(PAD).fill();
		rows.setBounds(0, 0, GreenPineappleGame.SCREEN_WIDTH, GreenPineappleGame.SCREEN_HEIGHT);

		rows.addActor(createTitleRow(skin));
		rows.addActor(createHostPlayerRow(skin));
		rows.addActor(createInvitePlayerRow(skin));
		rows.addActor(new Label("", skin));
		rows.addActor(createPlayRow(skin));

		stage.addActor(rows);

		stage.getCamera().viewportWidth = GreenPineappleGame.SCREEN_WIDTH;
		stage.getCamera().viewportHeight = GreenPineappleGame.SCREEN_HEIGHT;
		stage.getCamera().position.set(GreenPineappleGame.SCREEN_WIDTH / 2, GreenPineappleGame.SCREEN_HEIGHT / 2, 0);

		mainMenuController = new MainMenuController(this, skin, game);
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		stage.draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		batch.dispose();
		mainMenuController = null;
		clientPlayerRows.forEach(row -> row.dispose());
	}
	
	void addClientPlayerRow(Skin skin) {
		ClientPlayerRow playerRow = new ClientPlayerRow(skin);
		rows.addActorBefore(invitePlayerRow, playerRow);
		clientPlayerRows.add(playerRow);
	}

	private Actor createTitleRow(Skin skin) {
		return new Label("Operation: Green Pineapple", skin);
	}

	private Actor createHostPlayerRow(Skin skin) {
		HorizontalGroup columns = new HorizontalGroup().space(PAD * 5).pad(PAD).fill();
		columns.addActor(new CheckBox("Ready?", skin));
		columns.addActor(new TextArea("Player One", skin));
		columns.addActor(new Label(getIPAddress(), skin));

		CheckBox checkGuards = new CheckBox("Guards", skin);
		CheckBox checkThieves = new CheckBox("Thieves", skin);

		columns.addActor(checkGuards);
		columns.addActor(checkThieves);

		ButtonGroup<CheckBox> buttonGroupTeam = new ButtonGroup<>(checkGuards, checkThieves);
		buttonGroupTeam.setMaxCheckCount(1);

		return columns;
	}

	private Actor createInvitePlayerRow(Skin skin) {
		invitePlayerRow = new HorizontalGroup().space(PAD * 5).pad(PAD).fill();
		buttonInvitePlayer = new TextButton("Invite Player", skin);
		invitePlayerRow.addActor(buttonInvitePlayer);
		return invitePlayerRow;
	}

	private Actor createPlayRow(Skin skin) {
		HorizontalGroup columns = new HorizontalGroup().space(PAD * 5).pad(PAD).fill();
		buttonPlay = new TextButton("Play!", skin);
		columns.addActor(buttonPlay);
		return columns;
	}

	private String getIPAddress() {
		List<String> addresses = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			for (NetworkInterface ni : Collections.list(interfaces)) {
				for (InetAddress address : Collections.list(ni.getInetAddresses())) {
					if (address instanceof Inet4Address) {
						addresses.add(address.getHostAddress());
					}
				}
			}
		} catch (SocketException exception) {
			Gdx.app.error("Network", "Couldn't find local IP address!", exception);
		}

		// Print the contents of our array to a string. Yeah, should have used
		// StringBuilder
		String ipAddress = null;
		try {
			ipAddress = addresses.stream().filter(address -> address.startsWith("192")).findAny().get();
		} catch (NoSuchElementException exception) {
			Gdx.app.error("Network", "Couldn't find local IP address!", exception);
		}
		return ipAddress;
	}

}
