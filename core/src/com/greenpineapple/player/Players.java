package com.greenpineapple.player;

import java.util.HashMap;
import java.util.Objects;

import com.greenpineapple.net.Local;
import com.greenpineapple.net.NetworkSubscriptionService;

/**
 * Keeps a map of all the players in the game.
 */
public class Players {

	private static final HashMap<String, PlayerController> players = new HashMap<>();

	/**
	 * @param ipAddress
	 * @return true if a player exists for that address
	 */
	public static boolean hasPlayer(String ipAddress) {
		Objects.requireNonNull(ipAddress);
		return players.containsKey(ipAddress);
	}

	/**
	 * @param ipAddress
	 * @return the player for that address
	 * @throws IllegalArgumentException if there is no player for that address
	 */
	public static PlayerController getPlayer(String ipAddress) throws IllegalArgumentException {
		if (!hasPlayer(ipAddress)) {
			throw new IllegalArgumentException("Cannot find player for address: " + ipAddress);
		}
		return players.get(ipAddress);
	}
	
	/**
	 * @param ipAddress
	 * @return the player for that address, creating it if it doesn't already exist
	 */
	public static PlayerController createPlayer(String ipAddress) {
		if (!hasPlayer(ipAddress)) {
			players.put(ipAddress, createPlayerObject(ipAddress));
		}
		return getPlayer(ipAddress);
	}

	private static PlayerController createPlayerObject(String ipAddress) {
		if (ipAddress.equals(Local.getIPAddress())) {
			return new LocalPlayerController(ipAddress);
		} else {
			RemotePlayerController playerController = new RemotePlayerController(ipAddress);
			NetworkSubscriptionService.subscribe(playerController);
			return playerController;
		}
	}

}
