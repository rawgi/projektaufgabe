package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import framework.FallingImage;
import framework.Vertex;

public class Player extends FallingImage implements framework.Player{

	private boolean isHealing = false;
	
	private KeyListener keyListener = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				stopHeal();
				left();
				break;
			case KeyEvent.VK_D:
				stopHeal();
				right();
				break;
			case KeyEvent.VK_W:
				stopHeal();
				jump();
				playSound("jump.wav");
				break;
			case KeyEvent.VK_S:
				sprint();
				break;
			case KeyEvent.VK_SPACE:
				shoot();
				break;
			case KeyEvent.VK_F:
				stopHeal();
				blink();
				break;
			case KeyEvent.VK_E:
				heal();
				break;
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				stopMoveButFall();
				break;
			case KeyEvent.VK_D:
				stopMoveButFall();
				break;
			case KeyEvent.VK_W:
				jump();
				playSound("jump.wav");
				break;
			case KeyEvent.VK_SPACE:
				sprint();
				break;
			case KeyEvent.VK_F:
				blink();
				break;
			case KeyEvent.VK_E:
				stopHeal();
				break;
			}
		}
	};
	
	boolean isSprint = false;
	Timer sprintTimer = new Timer(4000, e ->{
		stopSprint();
	});
	
	public Player(Vertex corner) {
		super("player.gif", corner, new Vertex(0,0));
		setSpeed(2);
	}
	
	private void sprint(){
		if(!isSprint){
			setSpeed(4);
			movement.x *= 2;
			sprintTimer.start();
			isSprint = true;
		}
	}
	
	private void stopSprint(){
		if(isSprint){
			setSpeed(2);
			movement.x /= 2;
		}
		isSprint = false;
		sprintTimer.stop();
	}
	
	private void blink(){
		
	}
	
	private void heal(){
		if(!isJumping && !isHealing){
			stop();
			isHealing = true;
		}
	}
	
	private void stopHeal(){
		if(isHealing){
			isHealing = false;
		}
	}
	
	@Override
	public KeyListener getKeyListener() {
		return keyListener;
	}
	
	public boolean isHealing(){
		return isHealing;
	}
}
