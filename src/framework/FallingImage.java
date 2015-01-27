package framework;

import game.Game;
import game.Projectile;

import java.awt.Image;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class FallingImage extends ImageObject {
	static final double G = 9.81;

	public boolean isJumping = false;
	
	Image img_left;
	Image img_right;
	Image img_jumpLeft;
	Image img_jumpRight;
	Image img_fallLeft;
	Image img_fallRight;
	Image img_shootLeft;
	Image img_shootRight;
	
	List<Projectile> projectiles = new ArrayList<Projectile>();
	
	String soundFolder = "sounds/";
	
	int speed = 1;
	
	double v0;
	int t = 0;

	public FallingImage(String imageFileName, Vertex corner, Vertex movement) {
		super(imageFileName, corner, movement);
		createImages();
	}

	private void createImages(){
		img_right = initImage("_right");
		img_left = initImage("_left");
		img_jumpLeft = initImage("_jumpLeft");
		img_jumpRight = initImage("_jumpRight");
		img_fallLeft = initImage("_fallLeft");
		img_fallRight = initImage("_fallRight");
		img_shootLeft = initImage("_shootLeft");
		img_shootRight = initImage("_shootRight");
	}
	
	private Image initImage(String playerAction){
		URL resource = this.getClass().getClassLoader().getResource(completeUrl.substring(0, completeUrl.length()-4)+playerAction+completeUrl.substring(completeUrl.length()-4,completeUrl.length()));
		if(resource != null){
			ImageIcon icon = new ImageIcon(resource);
			return icon.getImage();
		}else{
			//soll versuchen, zumindest die blickrichtung bei zu behalten, falls das gesuchte img nicht vorhanden ist
			try{
				String s = playerAction.substring(playerAction.length()-4,playerAction.length());
				if(s.equals("Left")){
					return img_left;
				}else{
					return img;
				}
			}catch(Exception e){
				return img;
			}
		}
	}
	
	@Override
	public void move() {
		if (isJumping) {
			t++;
			double v = v0 + G * t / 200;
			movement.y = v;
		}
		super.move();
	}

	public void jump() {
		if (!isJumping) {
			startJump(-3);
		}
	}

	public void shoot(){
		int x = 0;
		if(lookingToTheRight()){
			x=6;
			img = img_shootRight;
		} else {
			x=-6;
			img = img_shootLeft;
		}
		projectiles.add(new Projectile(new Vertex(corner.x+Game.gameSizeScale+1,corner.y+Game.gameSizeScale/2), new Vertex(x,0)));
	}
	
	public boolean lookingToTheRight(){
		return img.equals(img_right) || img.equals(img_jumpRight) || img.equals(img_fallRight) || img.equals(img_shootRight);
	}
	
	public boolean lookingToTheLeft(){
		return !lookingToTheRight();
	}
	
	public void startJump(double v0) {
		isJumping = true;
		this.v0 = v0;
		t = 0;
		if(lookingToTheRight()){
			img = img_jumpRight;
		} else {
			img = img_jumpLeft;
		}
	}

	public void stop() {
		corner.move(movement.mult(-1.1));
		movement.x = 0;
		movement.y = 0;
		isJumping = false;
	}
	
	public void stopMoveButFall() {
		corner.move(movement.mult(-1.1));
		movement.x = 0;
	}

	public void stopFallButMove() {
		movement.y = 0;
		this.v0 = 0;
		isJumping = false;
		if(img == img_jumpLeft){
			img = img_left;
		} else {
			img = img_right;
		}
	}
	
	public void turn(){
		corner.move(movement.mult(-1.1));
		if(movement.x > 0){
			left();
		} else {
			right();
		}
	}
	
	public void left() {
		if (movement.x >= 0) {
			movement.x = -getSpeed();
			if(isJumping){
				img = img_jumpLeft;
			} else {
				img = img_left;
			}
		}
	}

	public void right() {
		if (movement.x <= 0) {
			movement.x = +getSpeed();
			if(isJumping){
				img = img_jumpRight;
			} else {
				img = img_right;
			}
		}
	}
	
	public boolean isInRangeOf(FallingImage that){
		return Math.abs(corner.x-that.corner.x) < 250;
	}
	
	public void invertMovementByTouchedSide(GeometricObject that){
		if(isGettingTouchedEastBy(that)){
			left();
		} else {
			right();
		}
	}

	public void playSound(String soundFile) {
		try {
			InputStream soundStream = getClass().getClassLoader()
					.getResourceAsStream(soundFolder+soundFile);

			AudioInputStream stream = AudioSystem.getAudioInputStream(soundStream);

			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getSpeed() {
		return speed;
	}

	public List<Projectile> getProjectiles(){
		return projectiles;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
