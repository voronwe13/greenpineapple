package Map;

import com.badlogic.gdx.math.Vector2;

public class Treasure {
	private Vector2 position;
	private boolean captured;
	
	public Treasure(Vector2 position){
		this.position = position;
		captured = false;
	}
	
	public void capture(){
		captured = true;
	}
	
	public void release(){
		captured = false;
	}
	
	public boolean isCaptured(){
		return captured;
	}
}
