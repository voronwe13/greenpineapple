package com.greenpineapple.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.greenpineapple.input.GPAInputProcessor;
import com.greenpineapple.player.GPAPlayer;

public class GPAGame extends ApplicationAdapter {
	
	String[] lines;

	SpriteBatch batch;
	private Texture guard; //the guard graphic
	private Texture robber; //the robber graphic
	private GPAPlayer guardplayer, robberplayer;
	public static final int screenwidth = 800, screenheight = 480;
	
    private Animation guardanimation, robberanimation;
    private Texture guardsheet, robbersheet;              // #4
    private TextureRegion[] guardframes, robberframes;             // #5
    private TextureRegion   guardcurrentframe, robbercurrentframe;           // #7

    float statetime;                                        // #8
	
	
	@Override
	public void create () {

		batch = new SpriteBatch();
		guardplayer = new GPAPlayer();
		robberplayer = new GPAPlayer();
		guardplayer.setTexture("mandalorian.png");
		robberplayer.setTexture("hansolo.png");
		guardplayer.setPosition(10,10);
		robberplayer.setPosition(300,300);
		GPAInputProcessor inputProcessor = new GPAInputProcessor();
		inputProcessor.setPlayer(guardplayer);
		Gdx.input.setInputProcessor(inputProcessor);
		FileHandle file = Gdx.files.internal("MapTest.txt");
		String text = file.readString();
		lines = text.split("\n");
		System.out.println(lines.toString());
        statetime = 0f;              
	}

	@Override
	public void render () {
		guardplayer.update();
		robberplayer.update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
		statetime += Gdx.graphics.getDeltaTime();
        guardcurrentframe = guardplayer.getCurrentFrame(statetime);
        robbercurrentframe = robberplayer.getCurrentFrame(statetime);
        batch.begin();
        batch.draw(guardcurrentframe, guardplayer.getPositionX(), guardplayer.getPositionY());
        batch.draw(robbercurrentframe, robberplayer.getPositionX(), robberplayer.getPositionY());
        batch.end();
	}

}

