package com.greenpineapple.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class GPAInputProcessor implements InputProcessor {

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode){
		case Keys.W:
			break;
		case Keys.A:
			break;
		case Keys.S:
			break;
		case Keys.D:
			break;
		}
			
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode){
		case Keys.W:
			break;
		case Keys.A:
			break;
		case Keys.S:
			break;
		case Keys.D:
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

}
