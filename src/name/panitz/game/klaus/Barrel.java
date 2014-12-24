package name.panitz.game.klaus;

import name.panitz.game.FallingImage;
import name.panitz.game.Vertex;

public class Barrel extends FallingImage {

	public Barrel(Vertex corner) {
		super("images/fass.gif", corner, new Vertex(1, 0));
	}

	public void fromTop(int wi) {
		getCorner().moveTo(new Vertex(Math.random() * (wi - 2 * 40) + 40, -40));
	}

	public void restart() {
		double oldX = movement.x;
		corner.move(movement.mult(-1.1));
		movement.x = -oldX;
		movement.y = 0;
		isJumping = false;
	}
}
