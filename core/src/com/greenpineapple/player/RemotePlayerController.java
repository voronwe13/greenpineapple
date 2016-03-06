package com.greenpineapple.player;

import java.util.Objects;

import com.badlogic.gdx.Gdx;
import com.greenpineapple.net.NetworkListener;
import com.greenpineapple.net.NetworkObject;
import com.greenpineapple.net.primitive.NetworkBoolean;
import com.greenpineapple.net.primitive.NetworkString;

public class RemotePlayerController extends PlayerController implements NetworkListener {

	RemotePlayerController(String ipAddress) {
		super(ipAddress);
	}

	@Override
	public boolean canConsume(NetworkObject object) {
		return Objects.requireNonNull(object).getSource().equals(getIPAddress());
	}

	@Override
	public void consume(NetworkObject object) throws IllegalArgumentException {
		if (!canConsume(object)) {
			throw new IllegalArgumentException("RemotePlayerController can't consume this object: " + object);
		}
		switch (object.getDescription()) {
		case PLAYER_NAME:
			setPlayerName(((NetworkString) object).getMessage());
			break;
		case PLAYER_READY:
			setPlayerReady(((NetworkBoolean) object).isChecked());
			break;
		case PLAYER_GUARD_TEAM:
			setPlayerGuardTeam(((NetworkBoolean) object).isChecked());
			break;
		case PLAYER_THIEF_TEAM:
			setPlayerThiefTeam(((NetworkBoolean) object).isChecked());
			break;
		case PLAYER_PHYSICAL_STATE:
			setPlayerPhysicalState((PhysicalState) object);
			break;
		default:
			Gdx.app.error("Controller",
					"RemotePlayerController doesn't recognize this object's description: " + object);
		}

	}
}
