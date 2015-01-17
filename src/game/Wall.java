package game;

import framework.ImageObject;
import framework.Vertex;

public class Wall extends ImageObject{

	public Wall(Vertex corner) {
		super("block.gif", corner, new Vertex(0,0));
	}

}
