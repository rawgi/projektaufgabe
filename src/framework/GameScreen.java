package framework;

import game.EndScreen;
import game.GameFrame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GameScreen extends JPanel {

	GameFramework game;
	GameFrame gFrame;
	
	ImageIcon backgroundImg;
	
	Timer timer = new Timer(1000 / 100, (ev) -> {
		game.step();
		game.checks();
		checkForEndOfGame();
		repaint();
		java.awt.Toolkit.getDefaultToolkit().sync();

		requestFocusInWindow();
	});
	
	public GameScreen(GameFramework game) {
		this.game = game;
		initAndStart();
	}
	
	private void initAndStart() {
		addKeyListener(game.getPlayer().getKeyListener());
		
		//background image laden
		URL resource = this.getClass().getClassLoader().getResource("images/background.jpg");
		backgroundImg = new ImageIcon(resource);
		
		timer.start();
	}

	private void checkForEndOfGame() {
		if (game.finished()) {
			timer.stop();
			gFrame = (GameFrame)SwingUtilities.getWindowAncestor(this);
			if(game.won()){
				gFrame.setContent(new EndScreen(true));
			}else{
				gFrame.setContent(new EndScreen(false));
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(game.getWidth(), game.getHeight());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImg.getImage(), 0, 0, 800, 600, null);
		game.paintTo(g);
	}
}
