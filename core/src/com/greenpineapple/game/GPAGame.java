package com.greenpineapple.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greenpineapple.input.GPAInputProcessor;
import com.greenpineapple.player.GPAPlayer;

public class GPAGame extends ApplicationAdapter {
	SpriteBatch batch;
	private Texture guard; //the guard graphic
	private Texture robber; //the robber graphic
	private GPAPlayer guardplayer, robberplayer;
	private int screenwidth, screenheight;
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		guard = new Texture(Gdx.files.internal("Circle.png"));
		robber = new Texture(Gdx.files.internal("Square.png"));
		guardplayer = new GPAPlayer();
		robberplayer = new GPAPlayer();
		guardplayer.setPosition(10,10);
		guardplayer.setPosition(300,300);
		GPAInputProcessor inputProcessor = new GPAInputProcessor();
		inputProcessor.setPlayer(guardplayer);
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void render () {
		guardplayer.update();
		robberplayer.update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        batch.begin();
        batch.draw(guard, guardplayer.getPositionX(), guardplayer.getPositionY());
        batch.draw(robber, robberplayer.getPositionX(), robberplayer.getPositionY());
        batch.end();
	}

	public int getScreenWidth() {
		return screenwidth;
	}
	
	public int getScreenHeight(){
		return screenheight;
	}

	public void setScreenDimensions(int width, int height) {
		screenwidth = width;
		screenheight = height;
	}
}
