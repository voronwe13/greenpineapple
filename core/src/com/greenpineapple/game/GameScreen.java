package com.greenpineapple.game;

import java.util.ArrayList;
import java.util.List;

import Map.MapClass;
import Map.Treasure;
import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.greenpineapple.GreenPineappleGame;
import com.greenpineapple.input.GPAInputProcessor;
import com.greenpineapple.player.GPAPlayer;
import com.greenpineapple.player.PlayerType;

public class GameScreen implements Screen {

	private String[] lines;

	private SpriteBatch batch;
	//private GPAPlayer guardplayer, robberplayer;
	private List<GPAPlayer> guardplayers, robberplayers;
	private List<Treasure> treasures;
	
    private Texture mapimage;
    private TextureRegion   guardcurrentframe, robbercurrentframe;

    float statetime;
    
    MapClass map;
	private PointLight pointlight2;
	private GPALighting lighting;
	
    public GameScreen(Game game) {
		batch = new SpriteBatch();
		map = new MapClass();
		List<Vector2> guardpositions = map.getGuardPositions();
		List<Vector2> robberpositions = map.getRobberPositions();
		List<Vector2> treasurepositions = map.getTreasurePositions();
		guardplayers = new ArrayList<GPAPlayer>();
		robberplayers = new ArrayList<GPAPlayer>();
		treasures = new ArrayList<Treasure>();
		for(int i=0; i<guardpositions.size(); i++){
			GPAPlayer guardplayer = new GPAPlayer(PlayerType.GUARD);
			guardplayer.setTexture("Guard Sprite polished small.png");
			int posx = (int)guardpositions.get(i).x;
			int posy = (int)guardpositions.get(i).y;
			guardplayer.setPosition(posx, posy);
			guardplayer.setMap(map);
			guardplayers.add(guardplayer);
		}
		for(int i=0; i<robberpositions.size(); i++){
			GPAPlayer robberplayer = new GPAPlayer(PlayerType.ROBBER);
			robberplayer.setTexture("Thief Sprite polished small.png");
			int posx = (int)robberpositions.get(i).x;
			int posy = (int)robberpositions.get(i).y;
			robberplayer.setPosition(posx, posy);
			robberplayer.setMap(map);
			robberplayers.add(robberplayer);
		}
		for(Vector2 treasureposition:treasurepositions){
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
		for(GPAPlayer guardplayer:guardplayers){
			guardplayer.update();
		}
		for(GPAPlayer robberplayer:robberplayers){
			robberplayer.update();
		}
		lighting.preRender(delta);
		statetime += Gdx.graphics.getDeltaTime();
        mapimage = map.getImage();
        batch.setProjectionMatrix(lighting.getCamera().combined);
        batch.begin();
        batch.draw(mapimage, 0, 0);
		for(GPAPlayer guardplayer:guardplayers){
	        guardcurrentframe = guardplayer.getCurrentFrame(statetime);
	        batch.draw(guardcurrentframe, guardplayer.getPositionX(), guardplayer.getPositionY());
		}
		for(GPAPlayer robberplayer:robberplayers){
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
