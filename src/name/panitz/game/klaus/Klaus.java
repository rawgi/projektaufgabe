package name.panitz.game.klaus;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;

import name.panitz.game.FallingImage;
import name.panitz.game.Player;
import name.panitz.game.Vertex;

public class Klaus extends FallingImage implements Player {

	private KeyListener keyListener = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				left();
				break;
			case KeyEvent.VK_RIGHT:
				right();
				break;
			case KeyEvent.VK_UP:
				jump();
				break;
			case KeyEvent.VK_DOWN:
				if (!isJumping)
					stop();
				break;
			}
		}
	};

	@Override
	public KeyListener getKeyListener() {
		return keyListener;
	}

	public Klaus(Vertex corner) {
		super("images/player.png", corner, new Vertex(0, 0));
	}
}
