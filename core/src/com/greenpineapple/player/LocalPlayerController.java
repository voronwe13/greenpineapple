package com.greenpineapple.player;

import com.greenpineapple.net.NetworkObjectDescription;
import com.greenpineapple.net.NetworkTransmitter;
import com.greenpineapple.net.primitive.NetworkBoolean;
import com.greenpineapple.net.primitive.NetworkString;

public class LocalPlayerController extends PlayerController {

	private final NetworkString playerName;
	private final NetworkBoolean playerReady;
	private final NetworkBoolean playerGuardTeam;
	private final NetworkBoolean playerThiefTeam;

	LocalPlayerController(String ipAddress) {
		super(ipAddress);

		playerName = new NetworkString(ipAddress, NetworkObjectDescription.PLAYER_NAME);
		setPlayerName("Player One");
		NetworkTransmitter.register(playerName);

		playerReady = new NetworkBoolean(ipAddress, NetworkObjectDescription.PLAYER_READY);
		setPlayerReady(false);
		NetworkTransmitter.register(playerReady);

		playerGuardTeam = new NetworkBoolean(ipAddress, NetworkObjectDescription.PLAYER_GUARD_TEAM);
		setPlayerGuardTeam(false);
		NetworkTransmitter.register(playerGuardTeam);

		playerThiefTeam = new NetworkBoolean(ipAddress, NetworkObjectDescription.PLAYER_THIEF_TEAM);
		setPlayerThiefTeam(false);
		NetworkTransmitter.register(playerThiefTeam);
		
		setPlayerPhysicalState(new PhysicalState(ipAddress, NetworkObjectDescription.PLAYER_PHYSICAL_STATE));
		NetworkTransmitter.register(getPlayerPhysicalState());
	}

	public void dispose() {
		playerName.dispose();
		playerReady.dispose();
		playerGuardTeam.dispose();
		playerThiefTeam.dispose();
		getPlayerPhysicalState().dispose();
	}

	@Override
	public void setPlayerName(String playerName) {
		super.setPlayerName(playerName);
		this.playerName.setMessage(playerName);
	}

	@Override
	public void setPlayerReady(boolean playerReady) {
		super.setPlayerReady(playerReady);
		this.playerReady.setChecked(playerReady);
	}

	@Override
	public void setPlayerGuardTeam(boolean playerGuardTeam) {
		super.setPlayerGuardTeam(playerGuardTeam);
		this.playerGuardTeam.setChecked(playerGuardTeam);
	}

	@Override
	public void setPlayerThiefTeam(boolean playerThiefTeam) {
		super.setPlayerThiefTeam(playerThiefTeam);
		this.playerThiefTeam.setChecked(playerThiefTeam);
	}
	
	@Override
	public void setPlayerPhysicalState(PhysicalState playerPhysicalState) {
		if (!getPlayerPhysicalState().equals(playerPhysicalState)) {
			getPlayerPhysicalState().dispose();
			NetworkTransmitter.register(playerPhysicalState);
		}
		super.setPlayerPhysicalState(playerPhysicalState);
	}

}
