package com.greenpineapple.net.primitive;

import java.util.Objects;

import com.greenpineapple.net.NetworkObject;
import com.greenpineapple.net.NetworkObjectDescription;

public class NetworkBoolean implements NetworkObject {
	private static final long serialVersionUID = 1L;

	private final String source;
	private final NetworkObjectDescription description;

	private boolean disposed = false;
	private boolean checked;

	public NetworkBoolean(String source, NetworkObjectDescription description) {
		this.source = Objects.requireNonNull(source);
		this.description = Objects.requireNonNull(description);
	}

	@Override
	public String getSource() {
		return source;
	}

	@Override
	public NetworkObjectDescription getDescription() {
		return description;
	}

	@Override
	public void dispose() {
		disposed = true;
	}

	@Override
	public boolean isDisposed() {
		return disposed;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isChecked() {
		return checked;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof NetworkBoolean)) {
			return false;
		}
		NetworkBoolean that = (NetworkBoolean) object;
		return Objects.equals(this.source, that.source) && Objects.equals(this.description, that.description)
				&& Objects.equals(this.disposed, that.disposed) && Objects.equals(this.checked, that.checked);
	}

	@Override
	public int hashCode() {
		return Objects.hash(source, description, disposed, checked);
	}
	
	@Override
	public String toString() {
		return source + "-" + description + ": "+ checked;
	}

}
