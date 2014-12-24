package name.panitz.game.klaus;

import name.panitz.game.ImageObject;
import name.panitz.game.Vertex;

public class Heart extends ImageObject {
	public Heart(Vertex corner) {
		super("images/heart.png", corner, new Vertex(0, 0));
	}
}
