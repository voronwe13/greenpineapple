package com.greenpineapple.menu;

import java.util.ArrayList;
import java.util.List;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.greenpineapple.GreenPineappleGame;
import com.greenpineapple.net.Local;

public class MainMenuScreen implements Screen {
	public static final float PAD = 5f;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Stage stage;

	private VerticalGroup rows;
	private List<RemotePlayerRow> remotePlayerRows = new ArrayList<>();
	private HorizontalGroup invitePlayerRow;

	TextButton buttonInvitePlayer;
	TextButton buttonPlay;
	TextField textPlayerName;
	CheckBox checkReady;
	CheckBox checkGuards;
	CheckBox checkThieves;

	public MainMenuScreen(Game game) {
		String ipAddress = Local.getIPAddress();

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

		rows = new VerticalGroup().space(PAD).pad(PAD).fill();
		rows.setBounds(0, 0, GreenPineappleGame.SCREEN_WIDTH, GreenPineappleGame.SCREEN_HEIGHT);

		rows.addActor(createTitleRow(skin));
		rows.addActor(createLocalPlayerRow(skin, ipAddress));
		rows.addActor(createInvitePlayerRow(skin));
		rows.addActor(new Label("", skin));
		rows.addActor(createPlayRow(skin));

		stage.addActor(rows);

		stage.getCamera().viewportWidth = GreenPineappleGame.SCREEN_WIDTH;
		stage.getCamera().viewportHeight = GreenPineappleGame.SCREEN_HEIGHT;
		stage.getCamera().position.set(GreenPineappleGame.SCREEN_WIDTH / 2, GreenPineappleGame.SCREEN_HEIGHT / 2, 0);

		new MainMenuController(this, skin, game, ipAddress);
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		remotePlayerRows.forEach(row -> row.render());

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
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
	}

	RemotePlayerRow addRemotePlayerRow(Skin skin) {
		RemotePlayerRow playerRow = new RemotePlayerRow(skin);
		remotePlayerRows.add(playerRow);
		rows.addActorBefore(invitePlayerRow, playerRow);
		return playerRow;
	}

	private Actor createTitleRow(Skin skin) {
		return new Label("Operation: Green Pineapple", skin);
	}

	private Actor createLocalPlayerRow(Skin skin, String ipAddress) {
		HorizontalGroup columns = new HorizontalGroup().space(PAD * 5).pad(PAD).fill();
		checkReady = new CheckBox(" Ready?", skin);
		checkReady.setDisabled(true);
		columns.addActor(checkReady);
		textPlayerName = new TextField("Player One", skin);
		columns.addActor(textPlayerName);
		columns.addActor(new Label(ipAddress, skin));

		checkGuards = new CheckBox(" Guards", skin);
		checkThieves = new CheckBox(" Thieves", skin);

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
}
