package mattes.game;

import javax.swing.JFrame;
import javax.swing.JPanel;

import name.panitz.game.GameScreen;

public class GameFrame extends JFrame{

	JPanel content;
	
	public static void main(String[]args){
		GameFrame gFrame = new GameFrame();
		gFrame.add(new GameScreen(new Game("testMap1")));
		gFrame.pack();
		gFrame.setVisible(true);
		gFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void setContent(JPanel content){
		this.content = content;
		setContentPane(content);
	}
}
