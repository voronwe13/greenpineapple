package com.greenpineapple.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.greenpineapple.net.NetworkObjectDescription;

import Map.MapClass;

public class GPAPlayer {

    private static final int FRAME_COLS = 8;
    private static final int FRAME_ROWS = 4;
	
	private int speedx = 3, speedy = 3;
	private PhysicalState status;
	private int movingx = 0, movingy = 0;
	private Texture spritesheet;
	private TextureRegion[][] spriteframes;
	private Animation animationleft, animationright, animationup, animationdown, currentanimation;
	private MapClass map;
	private Rectangle playerrect;
	
	public GPAPlayer(PlayerType playertype) {
		super();
		status = new PhysicalState("", NetworkObjectDescription.PLAYER_PHYSICAL_STATE);
		playerrect = new Rectangle();
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
		playerrect.x = status.positionx + movingx;
		if(!map.checkCollision(playerrect))
			status.positionx += movingx;
		else
			playerrect.x = status.positionx;
		playerrect.y = status.positiony + movingy;
		if(!map.checkCollision(playerrect))
			status.positiony += movingy;
		else
			playerrect.y = status.positiony;
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
        //int index = 0;
        spriteframes = new TextureRegion[FRAME_ROWS][FRAME_COLS];
        for (int i = 0; i < FRAME_ROWS; i++) {
        	for (int j = 0; j < FRAME_COLS; j++) {
                spriteframes[i][j] = tmp[i][j];
            }
        }
        playerrect.width = spriteframes[0][0].getRegionWidth();
        playerrect.height = spriteframes[0][0].getRegionHeight();
        animationup = new Animation(0.025f, spriteframes[0]);
        animationright = new Animation(0.025f, spriteframes[1]);
        animationdown = new Animation(0.025f, spriteframes[2]);
        animationleft = new Animation(0.025f, spriteframes[3]);
        currentanimation = animationright;

	}
	
	public Texture getTexture(){
		return spritesheet;
	}
	
	public TextureRegion getCurrentFrame(float statetime){
		if(movingx == 0 && movingy == 0){
			statetime = 0;
		}
		
        return currentanimation.getKeyFrame(statetime, true);
	}


	public void setMap(MapClass map) {
		this.map = map;
	}
}
