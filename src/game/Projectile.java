package game;

import framework.ImageObject;
import framework.Vertex;

public class Projectile extends ImageObject{

	public Projectile(Vertex corner, Vertex movement) {
		super("bullet.gif", corner, movement);
	}

}
