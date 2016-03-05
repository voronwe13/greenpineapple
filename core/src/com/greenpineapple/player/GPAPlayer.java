package com.greenpineapple.player;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.greenpineapple.GreenPineappleGame;

public class GPAPlayer implements Serializable {

    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 4;
	
	private int speedx = 5, speedy = 5;
	private PlayerStatus status;
	private int movingx = 0, movingy = 0;
	private Texture spritesheet;
	private TextureRegion[][] spriteframes;
	private Animation animationleft, animationright, animationup, animationdown, currentanimation;
	
	public GPAPlayer() {
		super();
		status = new PlayerStatus();
	}
	
	public int getPositionX() {
		return status.positionx;
	}

	public void setPositionX(int positionx) {
		status.positionx = positionx;
	}

	public int getPositionY() {
		return status.positiony;
	}

	public void setPositionY(int positiony) {
		status.positiony = positiony;
	}

	public void setPosition(int x, int y) {
		status.positionx = x;
		status.positiony = y;
	}
	
	public void moveLeft(){
		movingx -= speedx;
		status.facingdirection.x -= 1;
		if(movingx < 0)
			currentanimation = animationleft;
	}
	
	public void moveUp(){
		movingy += speedy;
		status.facingdirection.y += 1;
		if(movingy > 0)
			currentanimation = animationup;
	}
	
	public void moveRight(){
		movingx += speedx;
		status.facingdirection.x += 1;
		if(movingx > 0)
			currentanimation = animationright;
	}
	
	public void moveDown(){
		movingy -= speedy;
		status.facingdirection.y -= 1;
		if(movingy < 0)
			currentanimation = animationdown;
	}
	
	public void update(){
		status.positionx += movingx;
		if(status.positionx > GreenPineappleGame.SCREEN_WIDTH)
			status.positionx = GreenPineappleGame.SCREEN_WIDTH;
		if(status.positionx < 0)
			status.positionx = 0;
		status.positiony += movingy;
		if(status.positiony > GreenPineappleGame.SCREEN_HEIGHT)
			status.positiony = GreenPineappleGame.SCREEN_HEIGHT;
		if(status.positiony < 0)
			status.positiony = 0;
	}
	
	public void stopY() {
		movingy = 0;
	}

	public void stopX() {
		movingx = 0;
	}

	public void setTexture(String string) {
		spritesheet = new Texture(Gdx.files.internal(string));
        TextureRegion[][] tmp = TextureRegion.split(spritesheet, spritesheet.getWidth()/FRAME_COLS, spritesheet.getHeight()/FRAME_ROWS);              // #10
        int index = 0;
        spriteframes = new TextureRegion[FRAME_ROWS][FRAME_COLS];
        for (int i = 0; i < FRAME_ROWS; i++) {
        	for (int j = 0; j < FRAME_COLS; j++) {
                spriteframes[i][j] = tmp[i][j];
            }
        }
        animationdown = new Animation(0.025f, spriteframes[0]);
        animationleft = new Animation(0.025f, spriteframes[1]);
        animationright = new Animation(0.025f, spriteframes[2]);
        animationup = new Animation(0.025f, spriteframes[3]);
        currentanimation = animationright;

	}
	
	public Texture getTexture(){
		return spritesheet;
	}
	
	public TextureRegion getCurrentFrame(float statetime){
		if(movingx + movingy == 0){
			statetime = 0;
		}
		
        return currentanimation.getKeyFrame(statetime, true);
	}
}
