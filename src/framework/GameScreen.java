package framework;

import game.GameFrame;
import game.Menu;
import game.Settings;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

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
			gFrame.setContent(new Menu());
		}
	}
	
	private void checkForNewSpawn(){
		int delay = 0;
		switch(Settings.difficulty){
		case "easy":
			delay = 1300;
			break;
		case "hard":
			delay = 700;
			break;
		default: delay = 1000;
		}
		
		if(spawnCount == delay){
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
