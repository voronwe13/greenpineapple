package com.greenpineapple.player;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;

public class PlayerStatus implements Serializable {
	int positionx, positiony;
	Vector2 facingdirection;
	
	PlayerStatus(){
		facingdirection = new Vector2(0,0);
	}
}
