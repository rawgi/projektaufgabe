package mattes.game;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Credits extends JPanel{
	JLabel content = new JLabel();
	
	public Credits(){
		content.setText("Created by: \n Daniel Mattes \n \n Framework: Prof. Sven Eric Panitz");
		this.add(content);
	}
}
