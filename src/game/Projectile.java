package game;

import framework.ImageObject;
import framework.Vertex;

public class Projectile extends ImageObject{

	private double startVertex;
	
	public Projectile(Vertex corner, Vertex movement) {
		super("bullet.gif", corner, movement);
		startVertex = corner.x;
	}

	public boolean maxRange(){
		return Math.abs(startVertex-corner.x) > 250;
	}
	
}
