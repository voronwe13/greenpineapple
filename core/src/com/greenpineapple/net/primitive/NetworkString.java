package com.greenpineapple.net.primitive;

import java.util.Objects;

import com.greenpineapple.net.NetworkObject;
import com.greenpineapple.net.NetworkObjectDescription;

public class NetworkString implements NetworkObject {
	private static final long serialVersionUID = 1L;

	private final String source;
	private final NetworkObjectDescription description;

	private boolean disposed = false;
	private String message = "";

	public NetworkString(String source, NetworkObjectDescription description) {
		this.source = Objects.requireNonNull(source);
		this.description = Objects.requireNonNull(description);
	}

	public String getSource() {
		return source;
	}

	public NetworkObjectDescription getDescription() {
		return description;
	}

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
		return Objects.equals(this.source, that.source) && Objects.equals(this.description, that.description)
				&& Objects.equals(this.disposed, that.disposed) && Objects.equals(this.message, that.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(source, description, disposed, message);
	}
}
