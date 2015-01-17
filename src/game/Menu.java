package game;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import framework.GameScreen;

public class Menu extends JPanel{
	
	JButton newGame = new JButton("New Game");
	JButton settings = new JButton("Settings");
	JButton credits = new JButton("Credits");
	
	public Menu(){
		Settings.loadSettings();
		
		setLayout(new GridLayout(3,1));
		GridBagConstraints gbc = new GridBagConstraints();
		
		newGame.addActionListener(e -> {
			((GameFrame)SwingUtilities.windowForComponent(this)).setContent(new GameScreen(new Game()));
		});
		add(newGame,gbc);
		
		settings.addActionListener(e -> {
			((GameFrame)SwingUtilities.windowForComponent(this)).setContent(new Settings());
		});
		add(settings,gbc);
		
		credits.addActionListener(e -> {
			((GameFrame)SwingUtilities.windowForComponent(this)).setContent(new Credits());
		});
		add(credits,gbc);
	}
}