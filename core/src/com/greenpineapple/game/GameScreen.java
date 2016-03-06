package com.greenpineapple.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.greenpineapple.GreenPineappleGame;
import com.greenpineapple.input.GPAInputProcessor;
import com.greenpineapple.map.Map;
import com.greenpineapple.map.Treasure;
import com.greenpineapple.player.GPAPlayer;
import com.greenpineapple.player.PlayerController;
import com.greenpineapple.player.PlayerType;
import com.greenpineapple.player.Players;

public class GameScreen implements Screen {
	private SpriteBatch batch;

	private List<GPAPlayer> players = new ArrayList<>();

	private List<Treasure> treasures = new ArrayList<>();

	float statetime;

	private GPALighting lighting;

	private Map map;

	public GameScreen(GreenPineappleGame game) {
		batch = new SpriteBatch();
		map = game.getMap();

		addPlayers(map);

		List<Vector2> treasurepositions = map.getTreasurePositions();

		for (Vector2 treasureposition : treasurepositions) {
			Treasure treasure = new Treasure(treasureposition);
			treasures.add(treasure);
		}
		GPAInputProcessor inputProcessor = new GPAInputProcessor();
		inputProcessor.setPlayer(players.get(0));
		Gdx.input.setInputProcessor(inputProcessor);
		lighting = GPALighting.getInstance();
		lighting.initialize();

		statetime = 0f;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		for (GPAPlayer player : players) {
			player.update(map);
		}

		lighting.preRender(delta);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears
													// the screen.

		statetime += Gdx.graphics.getDeltaTime();
		batch.setProjectionMatrix(lighting.getCamera().combined);
		batch.begin();
		batch.draw(map.getImage(), 0, 0);
		for (GPAPlayer player : players) {
			TextureRegion playerCurrentFrame = player.getCurrentFrame(statetime);
			batch.draw(playerCurrentFrame, player.getPositionX(), player.getPositionY());
		}
		batch.end();
		lighting.postRender();
	}

	@Override
	public void resize(int width, int height) {
		lighting.resize(width, height);

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
	}

	private void addPlayers(Map map) {
		Vector2 guardPosition = map.getGuardPositions().get(0);
		Vector2 thiefPosition = map.getRobberPositions().get(0);

		for (PlayerController playerController : Players.getAllPlayers()) {
			if (playerController.isPlayerGuardTeam()) {
				GPAPlayer guardplayer = new GPAPlayer(PlayerType.GUARD);
				guardplayer.setTexture("Guard Sprite polished small.png");
				int posx = (int) guardPosition.x;
				int posy = (int) guardPosition.y;
				guardplayer.setPosition(posx, posy);
				players.add(guardplayer);
			} else if (playerController.isPlayerThiefTeam()) {
				GPAPlayer robberplayer = new GPAPlayer(PlayerType.ROBBER);
				robberplayer.setTexture("Thief Sprite polished small.png");
				int posx = (int) thiefPosition.x;
				int posy = (int) thiefPosition.y;
				robberplayer.setPosition(posx, posy);
				players.add(robberplayer);
			} else {
				Gdx.app.error("Player", "Player " + playerController.getPlayerName() + " has no team!");
			}
		}
	}

}
