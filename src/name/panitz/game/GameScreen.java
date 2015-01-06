package name.panitz.game;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import mattes.game.GameFrame;
import mattes.game.Menu;

@SuppressWarnings("serial")
public class GameScreen extends JPanel {

	GameFramework game;
	GameFrame gFrame;
	
	Timer timer = new Timer(1000 / 100, (ev) -> {
		game.step();
		game.checks();
		checkForEndOfGame();
		checkForNewSpawn();
		repaint();
		java.awt.Toolkit.getDefaultToolkit().sync();

		requestFocusInWindow();
	});
	
	private int spawnCount = 0;
	
	public GameScreen(GameFramework game) {
		this.game = game;
		initAndStart();
	}
	
	private void initAndStart() {
		addKeyListener(game.getPlayer().getKeyListener());
		timer.start();
	}

	private void checkForEndOfGame() {
		if (game.finished()) {
			timer.stop();
			gFrame = (GameFrame) SwingUtilities.getWindowAncestor(this);
			gFrame.setContent(new Menu(false));
		}
	}
	
	private void checkForNewSpawn(){
		if(spawnCount == 1000){
			game.spawnEnemy();
			spawnCount = 0;
		} else {
			spawnCount++;
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(game.getWidth(), game.getHeight());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.paintTo(g);
	}
}
