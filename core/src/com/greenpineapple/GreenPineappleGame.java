package com.greenpineapple;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.greenpineapple.map.Map;
import com.greenpineapple.menu.MainMenuScreen;
import com.greenpineapple.net.Local;
import com.greenpineapple.net.NetworkReceiver;
import com.greenpineapple.net.NetworkSubscriptionService;
import com.greenpineapple.net.NetworkTransmitter;
import com.greenpineapple.player.Players;

public class GreenPineappleGame extends Game {
	public static final int SCREEN_WIDTH = 1024;
	public static final int SCREEN_HEIGHT = 512;

	private Map map;
	
	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_INFO);
		
		NetworkReceiver.createServer();
		Players.createPlayer(Local.getIPAddress());

		//map = new Map("MapTest.txt");
		map = new Map("smallmap.txt");
		
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render() {
		NetworkTransmitter.transmit();
		NetworkSubscriptionService.distribute();
		super.render();
	}

	public Map getMap() {
		return map;
	}
}
