package game;

import javax.swing.JFrame;
import javax.swing.JPanel;

import framework.GameScreen;

public class GameFrame extends JFrame{

	JPanel content;
	
	public static void main(String[]args){
		GameFrame gFrame = new GameFrame();
		gFrame.add(new Menu());
		gFrame.pack();
		gFrame.setVisible(true);
		gFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		gFrame.setLocation(50,50);
		gFrame.setResizable(false);
	}
	
	public void setContent(JPanel content){
		this.content = content;
		setContentPane(content);
		pack();
	}
}
