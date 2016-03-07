package com.greenpineapple.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PaintingTreasure extends Treasure {

	private final int FRAME_COLS = 1;
	private final int FRAME_ROWS = 1;
	
	private Texture spritesheet;
	private TextureRegion[][] spriteframes;
	
	public PaintingTreasure(Vector2 position) {
		super(position);
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

	@Override
	public TextureRegion getCurrentFrame(float statetime) {
				// TODO Auto-generated method stub
		if(captured)
			return null;
		return currentanimation.getKeyFrame(statetime, true);
	}

}
