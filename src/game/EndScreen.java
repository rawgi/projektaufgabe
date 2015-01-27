package game;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import framework.GameScreen;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class EndScreen extends JPanel{

	GameFrame gFrame;

	JLabel status = new JLabel("lost");
	
	JButton newGame = new JButton("New Game");
	JButton exit = new JButton("exit to menu");
	
	public EndScreen(boolean won){
		
		setLayout(new GridLayout(3,1));
		GridBagConstraints gbc = new GridBagConstraints();
		
		if(won){
			status.setText("won");
		}
		add(status,gbc);
		gFrame = (GameFrame)SwingUtilities.getWindowAncestor(this);
		newGame.addActionListener(e -> {
			((GameFrame)SwingUtilities.windowForComponent(this)).setContent(new GameScreen(new Game()));
		});
		add(newGame,gbc);
		
		exit.addActionListener(e -> {
			((GameFrame)SwingUtilities.windowForComponent(this)).setContent(new Menu());
		});
		add(exit,gbc);
	}
}
