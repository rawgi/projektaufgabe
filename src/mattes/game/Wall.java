package mattes.game;

import name.panitz.game.ImageObject;
import name.panitz.game.Vertex;

public class Wall extends ImageObject{

	public Wall(Vertex corner) {
		super("block.gif", corner, new Vertex(0,0));
	}

}
