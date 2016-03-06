package com.greenpineapple.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Treasure {
	private static final int FRAME_COLS = 1;
	private static final int FRAME_ROWS = 1;
	
	private Vector2 position;
	private boolean captured;
	
	private Texture spritesheet;
	private TextureRegion[][] spriteframes;
	private Animation currentanimation;
	private Rectangle treasurerect;
	
	public Treasure(Vector2 position){
		this.position = position;
		captured = false;
		spritesheet = new Texture(Gdx.files.internal("loot.png"));
		TextureRegion[][] tmp = TextureRegion.split(spritesheet, spritesheet.getWidth() / FRAME_COLS,
				spritesheet.getHeight() / FRAME_ROWS); // #10
		// int index = 0;
		spriteframes = new TextureRegion[FRAME_ROWS][FRAME_COLS];
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				spriteframes[i][j] = tmp[i][j];
			}
		}
		treasurerect = new Rectangle();
		treasurerect.width = spriteframes[0][0].getRegionWidth();
		treasurerect.height = spriteframes[0][0].getRegionHeight();
		treasurerect.setPosition(position);
		currentanimation = new Animation(0.025f, spriteframes[0]);
		
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
		if(captured)
			return null;
		return currentanimation.getKeyFrame(statetime, true);
	}

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
