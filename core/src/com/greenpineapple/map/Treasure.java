package com.greenpineapple.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Treasure {
	private Vector2 position;
	private boolean captured;
	
	private Texture spritesheet;
	private TextureRegion[][] spriteframes;
	private Animation currentanimation;
	private Rectangle treasurerect;
	
	public Treasure(Vector2 position){
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

	public TextureRegion getCurrentFrame(float statetime) {
		// TODO Auto-generated method stub
		return null;
	}

	public float getPositionX() {
		// TODO Auto-generated method stub
		return position.x;
	}

	public float getPositionY() {
		// TODO Auto-generated method stub
		return position.y;
	}
}
