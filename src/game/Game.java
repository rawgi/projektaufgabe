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
	private List<Wall> walls = new ArrayList<Wall>();
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private Lifebar lifeBar;
	private List<Vertex> spawns = new ArrayList<Vertex>();
	
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
		
		lifeBar = new Lifebar(new Vertex(0,curLine*gameSizeScale),5);
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
		lifeBar.paintTo(g);
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
		}
	}

	@Override
	public void checks() {
		checkWallCollisions();
		checkPlayerEnemyCollisions();
		checkForGround();
		checkProjectiles();
		if(player.isHealing()){
			lifeBar.healOne();
		}
	}

	private void checkProjectiles() {
		List<Projectile> projectilesToRemove = new ArrayList<Projectile>();
		List<Enemy> enemiesHit = new ArrayList<Enemy>();
		
		//Projektile des Spielers durchlaufen
		for(Projectile p: player.getProjectiles()){
			for(Wall wall: walls){
				if(p.touches(wall)){
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
					if(p.touches(wall)){
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
			enemies.remove(enemy);
		}
	}

	//prüft, ob eine Figur von einer Plattform fällt.
	private void checkForGround(){
		boolean playerMustFall = true;
		List<Enemy> enemysToTurn = new ArrayList<Enemy>();

		for(Enemy enemy: enemies){
			boolean enemyMustFall = true;
			for(Wall wall: walls){
				if(enemy.isStandingOnTopOf(wall)) enemyMustFall = false;
				
				if(player.isStandingOnTopOf(wall)) playerMustFall = false;
			}
			if(enemyMustFall) enemysToTurn.add(enemy);
		}
		if(playerMustFall && !player.isJumping){
			player.startJump(1);
		}
		
		if(enemysToTurn.size() > 0){
			for(Enemy enemy: enemysToTurn){
				if(!enemy.isJumping){
						enemy.turn();
				}
			}
		}
	}
	
	private void checkPlayerEnemyCollisions() {
		Enemy enemyToDelete = null;
		
		for(Enemy enemy: enemies){
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
			
			for(Enemy enemyCheck: enemies){
					if(enemyCheck != enemy && enemyCheck.touches(enemy)){
						enemy.turn();
						enemyCheck.turn();
					}
			}
		}
		
		if(enemyToDelete != null){
			spawns.add(enemyToDelete.corner);
			enemies.remove(enemyToDelete);
		}
	}

	private void checkWallCollisions() {
		for(Wall wall: walls){
			if(player.touches(wall) && !player.isStandingOnTopOf(wall)){
				System.out.println("stop, weil wand");
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
		return enemies.size() == 0 ? true : false;
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
