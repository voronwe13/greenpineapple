package com.greenpineapple.player;

import com.greenpineapple.game.GPAGame;

public class GPAPlayer {

	private int speedx = 5, speedy = 5;
	private int positionx, positiony;
	private int movingx = 0, movingy = 0;
	
	public GPAPlayer() {
		super();
	}
	
	public int getPositionX() {
		return positionx;
	}

	public void setPositionX(int positionx) {
		this.positionx = positionx;
	}

	public int getPositionY() {
		return positiony;
	}

	public void setPositionY(int positiony) {
		this.positiony = positiony;
	}

	public void setPosition(int x, int y) {
		positionx = x;
		positiony = y;
	}
	
	public void moveLeft(){
		movingx = -speedx;
	}
	
	public void moveUp(){
		movingy = speedy;
	}
	
	public void moveRight(){
		movingx = speedx;
	}
	
	public void moveDown(){
		movingy = -speedy;
	}
	
	public void update(){
		positionx += movingx;
		if(positionx > GPAGame.screenwidth)
			positionx = GPAGame.screenwidth;
		if(positionx < 0)
			positionx = 0;
		positiony += movingy;
		if(positiony > GPAGame.screenheight)
			positiony = GPAGame.screenheight;
		if(positiony < 0)
			positiony = 0;
	}

	public void stopY() {
		movingy = 0;
	}

	public void stopX() {
		movingx = 0;
	}
}
