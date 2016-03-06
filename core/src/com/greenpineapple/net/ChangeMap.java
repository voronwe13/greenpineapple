package com.greenpineapple.net;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangeMap {

	private final Map<String, Map<NetworkObjectDescription, Integer>> hashCodes = new HashMap<>();
	private final Map<String, Map<NetworkObjectDescription, NetworkObject>> networkObjects = new HashMap<>();

	/**
	 * Note: this relies on the network objects hash code!
	 * @param object
	 * @return true and updates the change map if the object is not already
	 *         present in the map, or if the object is present in the map
	 *         but not equal to the map's version of the object.
	 */
	public boolean updateChangeMap(NetworkObject object) {
		if (Integer.valueOf(object.hashCode()).equals(
				hashCodes.getOrDefault(object.getSource(), Collections.emptyMap()).get(object.getDescription()))) {
			return false;
		}
		if (!hashCodes.containsKey(object.getSource())) {
			hashCodes.put(object.getSource(), new HashMap<>());
			networkObjects.put(object.getSource(), new HashMap<>());
		}
		hashCodes.get(object.getSource()).put(object.getDescription(), object.hashCode());
		networkObjects.get(object.getSource()).put(object.getDescription(), object);
		return true;
	}
	
	/**
	 * @return the latest version of all the objects in this change map.
	 */
	public List<NetworkObject> getNetworkObjects() {
		List<NetworkObject> list = new ArrayList<>();
		networkObjects.values().forEach(map -> list.addAll(map.values()));
		return list;
	}
}
