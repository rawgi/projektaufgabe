package game;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import util.FileUtil;

public class Settings extends JPanel{
	
	JButton exit = new JButton("exit");
	
	JLabel difficultyL = new JLabel("difficulty");
	JLabel levelL = new JLabel("level");
	
	public static String level;
	public static String difficulty;
	
	JComboBox<String> difficultyCB = new JComboBox<String>();
	JComboBox<String> levelCB = new JComboBox<String>();
	
	
	public Settings(){
		loadSettings();
		
		setLayout(new GridLayout(5,1));
		GridBagConstraints gbc = new GridBagConstraints();
		
		add(difficultyL, gbc);
		
		difficultyCB.addItem("easy");
		difficultyCB.addItem("medium");
		difficultyCB.addItem("hard");
		
		difficultyCB.setSelectedItem(difficulty);
		add(difficultyCB, gbc);
		
		add(levelL, gbc);
		try {
			for(String map: FileUtil.getFileListAsStringArray("./maps")){
				levelCB.addItem(map);
			}
		} catch (IOException e1) {
			levelCB.addItem("testMap1");
		}
		levelCB.setSelectedItem(level);
		add(levelCB, gbc);
		
		exit.addActionListener(e -> {
			saveSettings();
			((GameFrame)SwingUtilities.windowForComponent(this)).setContent(new Menu());
		});
		add(exit, gbc);
	}
	
	public static void loadSettings(){
		String[] lines = FileUtil.readTextLines("options/settings");
		
		for(String line: lines){
			switch(line.substring(0, 5)){
			case "level":
				level = line.substring(8, line.length());
				break;
			case "diffi":
				difficulty = line.substring(8, line.length());
				break;
			}
		}
	}
	
	private void saveSettings(){
		String[] lines = FileUtil.readTextLines("options/settings");
		
		/* TODO settings in feste Zeilen speichern, um versehentlich geänderte settingsdatei wieder zu korrigieren*/
		for(int x = 0; x < lines.length; x++){
			String line = lines[x];
			switch(line.substring(0, 5)){
			case "level":
				lines[x] =  "level = "+levelCB.getSelectedItem();
				break;
			case "diffi":
				lines[x] = "diffi = "+difficultyCB.getSelectedItem();
				break;
			}
		}
		
		FileUtil.writeFile("options/settings", lines);
	}
}