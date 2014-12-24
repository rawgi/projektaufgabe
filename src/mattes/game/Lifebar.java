package mattes.game;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import name.panitz.game.Vertex;
import name.panitz.game.Paintable;

public class Lifebar implements Paintable{

	List<Life> lifeList = new ArrayList();
	int lifes;
	Vertex corner;
	int gameSizeScale;
	
	boolean isHitable = true;
	
	Timer hitableDelay = new Timer(500, e -> {
		isHitable = true;
		stopTimer();
	});
	
	private void stopTimer(){
		hitableDelay.stop();
	}

	public Lifebar(Vertex corner, int lifes, int gameSizeScale) {
		this.corner = corner;
		this.lifes = lifes;
		this.gameSizeScale = gameSizeScale;
		create();
	}

	public void create(){
		for(int i = 0; i<lifes; i++){
			lifeList.add(new Life(new Vertex(i*gameSizeScale,corner.y)));
		}
	}
	
	public void removeLast(){
		if(lifeList.size() > 0 && isHitable){
			lifeList.remove(lifeList.size()-1);
			isHitable = false;
			hitableDelay.start();
		}
	}
	
	public boolean isEmpty(){
		return lifeList.size() == 0 ? true : false;
	}

	@Override
	public void paintTo(Graphics g) {
		if(!isHitable)g.setColor(Color.red);
		for(Life life: lifeList){
			life.paintTo(g);
		}
		if(!isHitable)g.setColor(null);
	}

	public boolean isHitable() {
		return isHitable;
	}
}
