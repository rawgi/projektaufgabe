package game;

import framework.FallingImage;
import framework.Vertex;

public class Enemy extends FallingImage{

	public Enemy(Vertex corner) {
		super("enemy.gif", corner, new Vertex(1,0));
	}	
}
