package com.greenpineapple.net.primitive;

import java.util.Objects;

import com.greenpineapple.net.NetworkObject;

public class NetworkString implements NetworkObject {
	private static final long serialVersionUID = 1L;

	private boolean disposed = false;

	private String message = "";

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void dispose() {
		disposed = true;
	}

	@Override
	public boolean isDisposed() {
		return disposed;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof NetworkString)) {
			return false;
		}
		NetworkString that = (NetworkString) object;
		return Objects.equals(this.disposed, that.disposed) && Objects.equals(this.message, that.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(disposed, message);
	}
}
