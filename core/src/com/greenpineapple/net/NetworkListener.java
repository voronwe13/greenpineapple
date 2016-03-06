package com.greenpineapple.net;

public interface NetworkListener {
	/**
	 * @param object
	 * @return true if this object consumes this network data
	 */
	public boolean canConsume(NetworkObject object);

	/**
	 * Attempts to consume the object.
	 * 
	 * @param object
	 * @throws IllegalArgumentException
	 *             if it can't consume the object.
	 */
	public void consume(NetworkObject object) throws IllegalArgumentException;
}
