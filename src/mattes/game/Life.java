package mattes.game;

import name.panitz.game.ImageObject;
import name.panitz.game.Vertex;

public class Life extends ImageObject{

	public Life(Vertex corner) {
		super("heart.png", corner, new Vertex(0,0));
	}

}
