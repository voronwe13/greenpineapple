package com.greenpineapple.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.greenpineapple.player.LocalPlayerController;
import com.greenpineapple.player.PlayerController;
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

		lighting = GPALighting.getInstance();
		lighting.initialize();

		statetime = 0f;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		for (GPAPlayer player : players) {
			player.update(map);
		}

		List<GPAPlayer> guards = players.parallelStream().filter(player -> player.isGuard())
				.collect(Collectors.toList());
		List<GPAPlayer> thieves = players.parallelStream().filter(player -> player.isThief())
				.collect(Collectors.toList());

		for (GPAPlayer thief : thieves) {
			if (guards.parallelStream().anyMatch(guard -> thief.getPlayerHitBox().overlaps(guard.getPlayerHitBox()))) {
				players.remove(thief);
				Gdx.app.log("Game Event", "A thief was caught!");
			}
		}

		players.parallelStream().filter(player -> player.isThief())
				.forEach(player -> players.parallelStream().filter(otherPlayer -> otherPlayer.isGuard())
						.anyMatch(otherPlayer -> player.getPlayerHitBox().overlaps(otherPlayer.getPlayerHitBox())));

		lighting.preRender(delta);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		statetime += Gdx.graphics.getDeltaTime();
		batch.setProjectionMatrix(lighting.getCamera().combined);
		batch.begin();
		batch.draw(map.getImage(), 0, 0);
//		for (Treasure treasure: treasures){
//			TextureRegion treasureFrame = treasure.getCurrentFrame(statetime);
//			batch.draw(treasureFrame, treasure.getPositionX(), treasure.getPositionY());			
//		}
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
			GPAPlayer player;
			if (playerController.isPlayerGuardTeam()) {
				player = new GPAPlayer(playerController);
				player.setTexture("Guard Sprite polished small.png");
				int posx = (int) guardPosition.x;
				int posy = (int) guardPosition.y;
				player.setPosition(posx, posy);
				players.add(player);
			} else if (playerController.isPlayerThiefTeam()) {
				player = new GPAPlayer(playerController);
				player.setTexture("Thief Sprite polished small.png");
				int posx = (int) thiefPosition.x;
				int posy = (int) thiefPosition.y;
				player.setPosition(posx, posy);
				players.add(player);
			} else {
				Gdx.app.error("Player", "Player " + playerController.getPlayerName() + " has no team!");
				continue;
			}

			if (playerController instanceof LocalPlayerController) {
				GPAInputProcessor inputProcessor = new GPAInputProcessor();
				inputProcessor.setPlayer(player);
				Gdx.input.setInputProcessor(inputProcessor);
			}
		}
	}

}
