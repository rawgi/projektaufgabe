package name.panitz.game.klaus;

import javax.swing.JFrame;
import name.panitz.game.GameScreen;

@SuppressWarnings("serial")
public class Start extends JFrame {
	public static void main(String[] args) throws Exception {
		Start f = new Start();
		f.add(new GameScreen(new HeartsOfKlaus()));
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
