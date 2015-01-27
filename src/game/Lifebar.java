package game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import framework.Paintable;
import framework.Vertex;

public class Lifebar implements Paintable{

	List<Life> lifeList = new ArrayList<Life>();
	int lifes;
	Vertex corner;
	int maxLifes;
	
	boolean isHitable = true;
	boolean isHealable = true;
	
	Timer hitableDelay = new Timer(500, e -> {
		isHitable = true;
		stopHitTimer();
	});
	
	Timer healDelay = new Timer(500, e -> {
		isHealable = true;
		stopHealTimer();
	});
	
	private void stopHitTimer(){
		hitableDelay.stop();
	}

	private void stopHealTimer(){
		healDelay.stop();
	}
	
	public Lifebar(Vertex corner, int lifes) {
		this.corner = corner;
		this.lifes = lifes;
		maxLifes = lifes;
		create();
	}

	public void create(){
		for(int i = 0; i<lifes; i++){
			lifeList.add(new Life(new Vertex(i*Game.gameSizeScale+corner.x,corner.y)));
		}
	}
	
	public void removeLast(){
		if(lifeList.size() > 0 && isHitable){
			lifeList.remove(lifeList.size()-1);
			isHitable = false;
			hitableDelay.start();
		}
	}
	
	public void healOne(){
		if(lifeList.size() < maxLifes && isHealable){
			lifeList.add(new Life(new Vertex((lifeList.size()+1)*Game.gameSizeScale,corner.y)));
			isHealable = false;
			healDelay.start();
		}
	}
	
	public boolean isEmpty(){
		return lifeList.size() == 0 ? true : false;
	}

	@Override
	public void paintTo(Graphics g) {
		for(Life life: lifeList){
			life.paintTo(g);
		}
	}

	public boolean isHitable() {
		return isHitable;
	}
}
