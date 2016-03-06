package com.greenpineapple.net;

import java.io.Serializable;

public interface NetworkObject extends Serializable {

	/**
	 * @return IP address this object is from.
	 */
	public String getSource();
	
	/**
	 * @return unique (per source) description of the object.
	 */
	public NetworkObjectDescription getDescription();
	
	/**
	 * Let the object know that it is no longer needed.
	 */
	public void dispose();
	
	/**
	 * @return true if the object is no longer needed. 
	 */
	public boolean isDisposed();
}
