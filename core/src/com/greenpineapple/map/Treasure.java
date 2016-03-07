package com.greenpineapple.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Treasure {
	
	protected boolean captured;
	
	private Vector2 position;
	protected Animation currentanimation;
	protected Rectangle treasurerect;
	
	public static Treasure getTreasure(Vector2 position){
		//TODO: return a random treasure type
		return new GemTreasure(position);
	}
	
	protected Treasure(Vector2 position){
		this.position = position;
		captured = false;
	}
	
	public void capture(){
		captured = true;
	}
	
	public void release(){
		captured = false;
	}
	
	public boolean isCaptured(){
		return captured;
	}

	public abstract TextureRegion getCurrentFrame(float statetime);

	public float getPositionX() {
		// TODO Auto-generated method stub
		return position.x;
	}

	public float getPositionY() {
		// TODO Auto-generated method stub
		return position.y;
	}
	
	public boolean checkCollision(Rectangle playerrect){
		return treasurerect.overlaps(playerrect);
	}
}
