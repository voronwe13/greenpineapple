package com.greenpineapple.net;

import java.io.Serializable;

public interface NetworkObject extends Serializable {

	/**
	 * Let the object know that it is no longer needed.
	 */
	public void dispose();
	
	/**
	 * @return true if the object is no longer needed. 
	 */
	public boolean isDisposed();
}
