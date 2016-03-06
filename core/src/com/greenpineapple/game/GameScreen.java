package com.greenpineapple.game;

import Map.MapClass;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.greenpineapple.input.GPAInputProcessor;
import com.greenpineapple.player.GPAPlayer;

public class GameScreen implements Screen {

	private String[] lines;

	private SpriteBatch batch;
	private Texture guard; //the guard graphic
	private Texture robber; //the robber graphic
	private GPAPlayer guardplayer, robberplayer;

	
    private Texture mapimage;
    private TextureRegion   guardcurrentframe, robbercurrentframe;

    float statetime;
    
    MapClass map;
	
    public GameScreen(Game game) {
		batch = new SpriteBatch();
		guardplayer = new GPAPlayer();
		robberplayer = new GPAPlayer();
		guardplayer.setTexture("BlueSpriteSheet.png");
		robberplayer.setTexture("RedSpriteSheet.png");
		guardplayer.setPosition(64,64);
		robberplayer.setPosition(300,300);
		GPAInputProcessor inputProcessor = new GPAInputProcessor();
		inputProcessor.setPlayer(guardplayer);
		Gdx.input.setInputProcessor(inputProcessor);
//		FileHandle file = Gdx.files.internal("MapTest.txt");
//		String text = file.readString();
//		lines = text.split("\n");
//		System.out.println(lines.toString());
		map = new MapClass();
		guardplayer.setMap(map);
		robberplayer.setMap(map);
        statetime = 0f;
	}
    
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		guardplayer.update();
		robberplayer.update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
		statetime += Gdx.graphics.getDeltaTime();
        guardcurrentframe = guardplayer.getCurrentFrame(statetime);
        robbercurrentframe = robberplayer.getCurrentFrame(statetime);
        mapimage = map.getImage();
        batch.begin();
        batch.draw(mapimage, 0, 0);
        batch.draw(guardcurrentframe, guardplayer.getPositionX(), guardplayer.getPositionY());
        batch.draw(robbercurrentframe, robberplayer.getPositionX(), robberplayer.getPositionY());
        batch.end();	
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
