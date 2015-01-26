package game;

import java.awt.Graphics;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import framework.GameFramework;
import framework.Vertex;
import util.FileUtil;

public class Game implements GameFramework{

	private Player player;
	private int points = 0;
	private List<Wall> walls = new ArrayList<Wall>();
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private Lifebar lifeBar;
	private List<Vertex> spawns = new ArrayList<Vertex>();
	private List<Coin> coins = new ArrayList<Coin>();
	
	private int width;
	private int height;
	
	public static int gameSizeScale = 40;
	
	public Game(){
		buildLevel(Settings.level);
	}
	
	void buildLevel(String level) {

		String[] lines = FileUtil.readTextLines("maps/"+level);
		int curLine = 0;
		
		for (String line : lines) {
			int col = 0;
			
			for (char c : line.toCharArray()) {
				switch (c) {
				case 'w':
					walls.add(new Wall(new Vertex(col*gameSizeScale, curLine*gameSizeScale)));
					break;
				case 'p':
					player = new Player(new Vertex(col*gameSizeScale, curLine*gameSizeScale));
					break;
				case 'e':
					enemies.add(new Enemy(new Vertex(col*gameSizeScale, curLine*gameSizeScale)));
					break;
				case 's':
					spawns.add(new Vertex(col*gameSizeScale, curLine*gameSizeScale));
					break;
				}
				col++;
			}
			width = col*gameSizeScale;
			curLine++;
		}
		height = curLine*gameSizeScale+gameSizeScale;
		
		lifeBar = new Lifebar(new Vertex(50,curLine*gameSizeScale),5);
	}
	
	@Override
	public void paintTo(Graphics g) {
		player.paintTo(g);
		for(Projectile p: player.getProjectiles()){
			p.paintTo(g);
		}
		for(Wall wall: walls){
			wall.paintTo(g);
		}
		for(Enemy enemy: enemies){
			enemy.paintTo(g);
			for(Projectile p: enemy.getProjectiles()){
				p.paintTo(g);
			}
		}
		for(Coin coin: coins){
			coin.paintTo(g);
		}
		lifeBar.paintTo(g);
		//soll auf der gleichen höhe, wie die lifeBar sein
		g.drawString(points+"", 0, (int)lifeBar.corner.y+gameSizeScale);
	}

	@Override
	public void step() {
		player.move();
		for(Projectile p: player.getProjectiles()){
			p.move();
		}
		
		for(Enemy enemy: enemies){
			enemy.move();
			for(Projectile p: enemy.getProjectiles()){
				p.move();
			}

			for(Projectile p: enemy.getProjectiles()){
				p.move();
			}
		}
	}

	@Override
	public void checks() {
		checkWallCollisions();
		checkPlayerEnemyInteractions();
		checkForGround();
		checkProjectiles();
		if(player.isHealing()){
			lifeBar.healOne();
		}
		checkCoinsCollected();
	}

	private void checkProjectiles() {
		List<Projectile> projectilesToRemove = new ArrayList<Projectile>();
		List<Enemy> enemiesHit = new ArrayList<Enemy>();
		
		//Projektile des Spielers durchlaufen
		for(Projectile p: player.getProjectiles()){
			for(Wall wall: walls){
				if(p.touches(wall) || p.maxRange()){
					projectilesToRemove.add(p);
				}
			}
			for(Enemy enemy: enemies){
				if(p.touches(enemy)){
					projectilesToRemove.add(p);
					enemiesHit.add(enemy);
				}
			}
		}
		for(Projectile p: projectilesToRemove){
			player.getProjectiles().remove(p);
		}
		projectilesToRemove.clear();
		//Projektile aller Gegner durchlaufen
		for(Enemy enemy: enemies){
			for(Projectile p: enemy.getProjectiles()){
				for(Wall wall: walls){
					if(p.touches(wall) || p.maxRange()){
						projectilesToRemove.add(p);
					}
				}
				if(p.touches(player)){
					projectilesToRemove.add(p);
					lifeBar.removeLast();
				}
			}
			for(Projectile p: projectilesToRemove){
				enemy.getProjectiles().remove(p);
			}
		}
		for(Enemy enemy: enemiesHit){
			coins.add(new Coin(enemy.getCorner()));
			enemies.remove(enemy);
		}
	}


	private void checkForGround(){
		boolean playerMustFall = true;
		List<Enemy> enemysToTurn = new ArrayList<Enemy>();

		//if-bedingung, wegen bug, wenn kein gegner mehr uebrig ist, aber der punkt noch eingesammelt werden muss
		if(!enemies.isEmpty()){	
			for(Enemy enemy: enemies){
				boolean enemyMustFall = true;
				//prüft, ob eine Figur von einer Plattform fällt.
				for(Wall wall: walls){
					if(enemy.isStandingOnTopOf(wall)) enemyMustFall = false;
					
					if(player.isStandingOnTopOf(wall)) playerMustFall = false;
				}
				if(enemyMustFall) enemysToTurn.add(enemy);
			}
		}else{
			for(Wall wall: walls){	
				if(player.isStandingOnTopOf(wall)){
					playerMustFall = false;
					break;
				}
			}
		}
		
		//lässt den spieler fallen
		if(playerMustFall && !player.isJumping){
			player.startJump(1);
		}
		
		//lässt die entsprechenden Gegner umdrehen
		for(Enemy enemy: enemysToTurn){
			if(!enemy.isJumping){
					enemy.turn();
			}
		}
	}
	
	private void checkPlayerEnemyInteractions() {
		
		//keine Liste, da der Spieler nur mit einem Gegner interagieren können soll.
		//steht er z.B. auf 2 Gegnern, so kann er immer nur einen treffen.
		Enemy enemyToDelete = null;
		
		for(Enemy enemy: enemies){
			
			// berührt der Spieler den Gegner?
			if(player.isStandingOnTopOf(enemy)){
				enemyToDelete = enemy;
				player.stopFallButMove();
				player.startJump(-1.5);
			} else {
				if(player.touches(enemy)){
					lifeBar.removeLast();
					enemy.invertMovementByTouchedSide(player);
					player.stopMoveButFall();
				}
			}
			
			//berührt irgendein anderer Gegner diesen Gegner
			for(Enemy enemyCheck: enemies){
					if(enemyCheck != enemy && enemyCheck.touches(enemy)){
						enemy.turn();
						enemyCheck.turn();
					}
			}
			
			enemy.shootAt(player);
		}
		
		if(enemyToDelete != null){
			coins.add(new Coin(enemyToDelete.getCorner()));
			spawns.add(enemyToDelete.getCorner());
			enemies.remove(enemyToDelete);
		}
	}

	private void checkCoinsCollected(){
		List<Coin> collectedCoins = new ArrayList<Coin>();
		for(Coin coin: coins){
			if(player.touches(coin)){
				collectedCoins.add(coin);
				points++;
			}
		}
		//meistens eh nur 1, aber evtl liegen mal 2 nebereinander
		for(Coin coin: collectedCoins){
			coins.remove(coin);
		}
	}
	
	private void checkWallCollisions() {
		for(Wall wall: walls){
			if(player.touches(wall) && !player.isStandingOnTopOf(wall)){
					player.stopMoveButFall();
			}
			
			if(player.isJumping && (player.isStandingOnTopOf(wall) || wall.isStandingOnTopOf(player))){
				player.stopFallButMove();
			}
			
			for(Enemy enemy: enemies){
				if(enemy.touches(wall)){
					enemy.turn();
				}
				if(enemy.isJumping && (enemy.isStandingOnTopOf(wall) || wall.isStandingOnTopOf(enemy))){
					enemy.stopFallButMove();
				}
			}
		}
	}
	
	public void spawnEnemy(){
		//sollten zu viele Gegner auf dem Feld sein, verliert der Spieler ein Leben
		//für jeden zusätzlichen Spawnenden Gegner
		if(spawns.size() > 0){
			enemies.add(new Enemy(spawns.get((int)(Math.random()*spawns.size()))));
		} else {
			lifeBar.removeLast();
		}
	}
	
	InputStream soundStream(String soundFile){
		return getClass().getClassLoader().getResourceAsStream(soundFile);
	}
	
	@Override
	public boolean lost() {
		return lifeBar.isEmpty() ? true : false;
	}

	@Override
	public boolean won() {
		return enemies.size() == 0 && coins.size() == 0 ? true : false;
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}
	
	public int getGameSizeScale(){
		return gameSizeScale;
	}
}
