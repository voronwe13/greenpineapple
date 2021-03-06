package com.greenpineapple.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.greenpineapple.player.GPAPlayer;

public class GPAInputProcessor extends InputAdapter {

	private GPAPlayer player;

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode){
		case Keys.W:
			player.moveUp();
			break;
		case Keys.A:
			player.moveLeft();
			break;
		case Keys.S:
			player.moveDown();
			break;
		case Keys.D:
			player.moveRight();
			break;
		}
			
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode){
		case Keys.W:
			player.moveDown();
			break;
		case Keys.A:
			player.moveRight();
			break;
		case Keys.S:
			player.moveUp();
			break;
		case Keys.D:
			player.moveLeft();
			break;
		}
		return false;
	}

	public void setPlayer(GPAPlayer player){
		this.player = player;
		
	}
	
}
