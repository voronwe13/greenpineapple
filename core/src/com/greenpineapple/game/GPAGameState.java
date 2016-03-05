package com.greenpineapple.game;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.List;

import com.greenpineapple.player.GPAPlayer;

public class GPAGameState implements Serializable {
	
	public List<GPAPlayer> guards, players;
	
}
