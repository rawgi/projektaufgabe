package mattes.game;

import javax.swing.JButton;
import javax.swing.JPanel;

import name.panitz.game.GameScreen;

public class Menu extends JPanel{
	
	JButton newGame = new JButton("New Game");
	JButton keepPlaying = new JButton("Continue");
	JButton settings = new JButton("Settings");
	JButton credits = new JButton("Credits");
	
	public Menu(boolean inGame){
		newGame.addActionListener(e -> {
			((GameFrame)this.getParent().getParent()).setContent(new GameScreen(new Game("testMap1")));
		});
		add(newGame);
		
		if(inGame){
			keepPlaying.addActionListener(e -> {
				((GameFrame)this.getParent()).setContent(new GameScreen(new Game("testMap1")));
			});
			add(keepPlaying);
		}
		
		settings.addActionListener(e -> {
			((GameFrame)this.getParent()).setContent(new GameScreen(new Game("testMap1")));
		});
		add(settings);
		
		credits.addActionListener(e -> {
			((GameFrame)this.getParent()).setContent(new GameScreen(new Game("testMap1")));
		});
		add(credits);
	}
}
