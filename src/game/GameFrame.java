package game;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame{

	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	public static void main(String[]args){
		GameFrame gFrame = new GameFrame();
		gFrame.setContent(new Menu());
		gFrame.setVisible(true);
		gFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		gFrame.setResizable(false);
	}
	
	public void setContent(JPanel content){
		setContentPane(content);
		Dimension d = new Dimension(content.getPreferredSize());
		setLocation(dim.width/2-d.width/2, dim.height/2-d.height/2);
		pack();
	}
}