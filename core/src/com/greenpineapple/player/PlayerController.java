package com.greenpineapple.player;

import java.util.Objects;

import com.greenpineapple.Controller;

/**
 * Tracks the state of a player in the game.
 */
public abstract class PlayerController {
	private final String ipAddress;

	private String playerName;
	private boolean playerReady;
	private boolean playerGuardTeam;
	private boolean playerThiefTeam;

	PlayerController(String ipAddress) {
		this.ipAddress = Objects.requireNonNull(ipAddress);
	}
	
	public String getIPAddress() {
		return ipAddress;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = Objects.requireNonNull(playerName);
	}

	public boolean isPlayerReady() {
		return playerReady;
	}

	public void setPlayerReady(boolean playerReady) {
		this.playerReady = playerReady;
	}

	public boolean isPlayerGuardTeam() {
		return playerGuardTeam;
	}

	public void setPlayerGuardTeam(boolean playerGuardTeam) {
		this.playerGuardTeam = playerGuardTeam;
	}

	public boolean isPlayerThiefTeam() {
		return playerThiefTeam;
	}

	public void setPlayerThiefTeam(boolean playerThiefTeam) {
		this.playerThiefTeam = playerThiefTeam;
	}
}
