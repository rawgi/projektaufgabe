package game;

import framework.ImageObject;
import framework.Vertex;

public class Life extends ImageObject{

	public Life(Vertex corner) {
		super("heart.png", corner, new Vertex(0,0));
	}

}
