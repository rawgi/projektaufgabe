package mattes.game;

import name.panitz.game.FallingImage;
import name.panitz.game.Vertex;

public class Enemy extends FallingImage{

	public Enemy(Vertex corner) {
		super("enemy.gif", corner, new Vertex(1,0));
	}	
}
