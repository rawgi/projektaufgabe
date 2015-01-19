package game;

import javax.swing.Timer;

import framework.FallingImage;
import framework.GeometricObject;
import framework.Vertex;

public class Enemy extends FallingImage{

	private Timer shootDelay = new Timer(1000, e -> {

		stopShootTimer();
	});
	
	public Enemy(Vertex corner) {
		super("enemy.gif", corner, new Vertex(1,0));
	}
	
	public boolean isLookingAt(GeometricObject that){
		return (movement.x > 0 && that.isRightOf(this)) || (movement.x < 0 && that.isLeftOf(this));
	}
	
	public void shootAt(FallingImage that){

		//wenn der spieler nah genug am gegner ist,
		//spieler und gegner ca auf gleicher hoehe sind
		//und der gegner in richtung des Spielers schaut
		if(isInRangeOf(that)
				&& !(isAbove(that) || that.isAbove(this))
				&& isLookingAt(that)
				&& !shootDelay.isRunning()){
			shoot();
			shootDelay.start();
		}
	}
	
	private void stopShootTimer(){
		shootDelay.stop();
	}
}
