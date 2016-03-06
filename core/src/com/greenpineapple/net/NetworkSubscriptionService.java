package com.greenpineapple.net;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NetworkSubscriptionService {

	private static Set<NetworkListener> listeners = new HashSet<>();
	
	private static ChangeMap changeMap = new ChangeMap();
	
	public static void subscribe(NetworkListener listener) {
		listeners.add(listener);
		
		// Give the listener the latest values.
		for (NetworkObject object : changeMap.getNetworkObjects()) {
			if (listener.canConsume(object)) {
				listener.consume(object);
			}
		}
	}
	
	public static void unsubscribe(NetworkListener listener) {
		listeners.remove(listener);
	}

	public static void distribute() {
		List<NetworkObject> networkObjects = NetworkReceiver.retrieveUpdates();
		
		for (NetworkObject object : networkObjects) {
			changeMap.updateChangeMap(object);
			
			for (NetworkListener listener : listeners) {
				if (listener.canConsume(object)) {
					listener.consume(object);
				}
			}
		}
	}
}
