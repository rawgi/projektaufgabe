package mattes.game;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import name.panitz.game.GameScreen;

public class Menu extends JPanel{
	
	JButton newGame = new JButton("New Game");
	JButton keepPlaying = new JButton("Continue");
	JButton settings = new JButton("Settings");
	JButton credits = new JButton("Credits");

	GameFrame gFrame;
	
	public Menu(boolean inGame){
		newGame.addActionListener(e -> {
			gFrame = (GameFrame) SwingUtilities.windowForComponent(this);
			gFrame.setContent(new GameScreen(new Game("testMap1")));
		});
		add(newGame);
		
		if(inGame){
			keepPlaying.addActionListener(e -> {
				gFrame.setContent(new GameScreen(new Game("testMap1")));
			});
			add(keepPlaying);
		}
		
		settings.addActionListener(e -> {
			gFrame.setContent(new GameScreen(new Game("testMap1")));
		});
		add(settings);
		
		credits.addActionListener(e -> {
			gFrame.setContent(new GameScreen(new Game("testMap1")));
		});
		add(credits);
	}
}
