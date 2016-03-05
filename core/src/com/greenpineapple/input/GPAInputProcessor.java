package com.greenpineapple.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.greenpineapple.player.GPAPlayer;

public class GPAInputProcessor implements InputProcessor {

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

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setPlayer(GPAPlayer player){
		this.player = player;
		
	}
	
}
