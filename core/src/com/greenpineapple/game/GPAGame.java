package com.greenpineapple.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.greenpineapple.input.GPAInputProcessor;

public class GPAGame extends ApplicationAdapter {
	
	String[] lines;
	
	@Override
	public void create () {
		GPAInputProcessor inputProcessor = new GPAInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		FileHandle file = Gdx.files.internal("MapTest.txt");
		String text = file.readString();
		lines = text.split("\n");
		System.out.println(lines.toString());
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
		
	}
}
