package game;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Credits extends JPanel{
	JLabel content = new JLabel();
	JButton exit = new JButton("exit");
	public Credits(){
		
		setLayout(new GridLayout(2,1));
		GridBagConstraints gbc = new GridBagConstraints();
		content.setText("<html><body style='text-align: center'>Created by: <br /> <div style='color: red'>Daniel Mattes</div> <br /><br /> Framework:<br />Prof. Sven Eric Panitz</body></html>");
		add(content,gbc);
		exit.addActionListener(e -> {
			((GameFrame)SwingUtilities.windowForComponent(this)).setContent(new Menu());
		});
		add(exit,gbc);
	}
}
