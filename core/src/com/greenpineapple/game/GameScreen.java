package com.greenpineapple.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.greenpineapple.GreenPineappleGame;
import com.greenpineapple.input.GPAInputProcessor;
import com.greenpineapple.map.Map;
import com.greenpineapple.map.Treasure;
import com.greenpineapple.player.GPAPlayer;
import com.greenpineapple.player.PlayerType;

import box2dLight.PointLight;

public class GameScreen implements Screen {

	private String[] lines;

	private SpriteBatch batch;
	// private GPAPlayer guardplayer, robberplayer;
	private List<GPAPlayer> guardplayers, robberplayers;
	private List<Treasure> treasures;

    float statetime;
    
	private Texture mapimage;
	private TextureRegion guardcurrentframe, robbercurrentframe;

    Map map;
	private PointLight pointlight2;
	private GPALighting lighting;
	
    public GameScreen(GreenPineappleGame game) {
		batch = new SpriteBatch();
		map = game.getMap();
		List<Vector2> guardpositions = map.getGuardPositions();
		List<Vector2> robberpositions = map.getRobberPositions();
		List<Vector2> treasurepositions = map.getTreasurePositions();
		guardplayers = new ArrayList<GPAPlayer>();
		robberplayers = new ArrayList<GPAPlayer>();
		treasures = new ArrayList<Treasure>();
		for (int i = 0; i < guardpositions.size(); i++) {
			GPAPlayer guardplayer = new GPAPlayer(PlayerType.GUARD);
			guardplayer.setTexture("Guard Sprite polished small.png");
			int posx = (int) guardpositions.get(i).x;
			int posy = (int) guardpositions.get(i).y;
			guardplayer.setPosition(posx, posy);
			guardplayer.setMap(map);
			guardplayers.add(guardplayer);
		}
		for (int i = 0; i < robberpositions.size(); i++) {
			GPAPlayer robberplayer = new GPAPlayer(PlayerType.ROBBER);
			robberplayer.setTexture("Thief Sprite polished small.png");
			int posx = (int) robberpositions.get(i).x;
			int posy = (int) robberpositions.get(i).y;
			robberplayer.setPosition(posx, posy);
			robberplayer.setMap(map);
			robberplayers.add(robberplayer);
		}
		for (Vector2 treasureposition : treasurepositions) {
			Treasure treasure = new Treasure(treasureposition);
			treasures.add(treasure);
		}
		GPAInputProcessor inputProcessor = new GPAInputProcessor();
		inputProcessor.setPlayer(guardplayers.get(0));
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
		for (GPAPlayer guardplayer : guardplayers) {
			guardplayer.update();
		}
		for (GPAPlayer robberplayer : robberplayers) {
			robberplayer.update();
		}

		lighting.preRender(delta);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears
													// the screen.

		statetime += Gdx.graphics.getDeltaTime();
        mapimage = map.getImage();
        batch.setProjectionMatrix(lighting.getCamera().combined);
        batch.begin();
        batch.draw(mapimage, 0, 0);
		for(GPAPlayer guardplayer:guardplayers){
	        guardcurrentframe = guardplayer.getCurrentFrame(statetime);
	        batch.draw(guardcurrentframe, guardplayer.getPositionX(), guardplayer.getPositionY());
		}
		for (GPAPlayer robberplayer : robberplayers) {
			robbercurrentframe = robberplayer.getCurrentFrame(statetime);
			batch.draw(robbercurrentframe, robberplayer.getPositionX(), robberplayer.getPositionY());
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
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
