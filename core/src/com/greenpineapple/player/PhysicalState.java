package com.greenpineapple.player;

import java.util.Objects;

import com.badlogic.gdx.math.Vector2;
import com.greenpineapple.net.NetworkObject;
import com.greenpineapple.net.NetworkObjectDescription;

public class PhysicalState implements NetworkObject {
	private static final long serialVersionUID = 1L;

	private final String source;
	private final NetworkObjectDescription descrption;

	private boolean disposed = false;

	int positionx, positiony;
	Vector2 facingdirection = new Vector2();

	PhysicalState(String source, NetworkObjectDescription descrption) {
		this.source = Objects.requireNonNull(source);
		this.descrption = Objects.requireNonNull(descrption);
	}

	@Override
	public String getSource() {
		return source;
	}

	@Override
	public NetworkObjectDescription getDescription() {
		return descrption;
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
		if (!(object instanceof PhysicalState)) {
			return false;
		}
		PhysicalState that = (PhysicalState) object;
		return Objects.equals(this.source, that.source) && Objects.equals(this.descrption, that.descrption)
				&& Objects.equals(this.disposed, that.disposed) && Objects.equals(this.positionx, that.positionx)
				&& Objects.equals(this.positiony, that.positiony)
				&& Objects.equals(this.facingdirection, that.facingdirection);
	}

	@Override
	public int hashCode() {
		return Objects.hash(source, descrption, disposed, positionx, positiony, facingdirection);
	}
}
