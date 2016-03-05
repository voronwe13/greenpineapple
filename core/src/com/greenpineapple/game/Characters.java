package com.greenpineapple.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Characters extends ApplicationAdapter {
	SpriteBatch batch;
	private Texture guard; //the guard graphic
	private Texture robber; //the robber graphic

	@Override
	public void create () {
		batch = new SpriteBatch();
		guard = new Texture(Gdx.files.internal("Circle.png"));
		robber = new Texture(Gdx.files.internal("Square.png"));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        batch.begin();
        batch.draw(guard,0,0);
        batch.draw(robber, 300, 300);
        batch.end();
	}
	
	public Texture returnGuard()
	{
		return this.guard;
	}
	
	public Texture returnRobber()
	{
		return this.robber;
	}
}
