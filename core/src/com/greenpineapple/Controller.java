package com.greenpineapple;

import com.greenpineapple.net.NetworkListener;

public interface Controller extends NetworkListener {

	/**
	 * Remove any persistent objects from this object. Note if this is called,
	 * the controller shouldn't be used again.
	 */
	public void dispose();
}
