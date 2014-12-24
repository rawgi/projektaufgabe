package name.panitz.game.klaus;

import name.panitz.game.ImageObject;
import name.panitz.game.Vertex;

public class Wall extends ImageObject {
	public Wall(Vertex corner) {
		super("images/wall.png", corner, new Vertex(0, 0));
	}
}
