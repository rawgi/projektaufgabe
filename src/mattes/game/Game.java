package mattes.game;

import java.awt.Graphics;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import name.panitz.game.GameFramework;
import name.panitz.game.Vertex;
import name.panitz.game.util.FileUtil;

public class Game implements GameFramework{

	private Player player;
	private List<Wall> walls = new ArrayList<Wall>();
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private Lifebar lifeBar;
	private List<Vertex> spawns = new ArrayList<Vertex>();
	
	private int width;
	private int height;
	
	private int gameSizeScale = 40;
	
	public Game(String level){
		buildLevel(level);
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
		
		lifeBar = new Lifebar(new Vertex(0,curLine*gameSizeScale),5,gameSizeScale);
	}
	
	@Override
	public void paintTo(Graphics g) {
		if(finished()){
			g.drawString(won() ? "gewonnen" : "verloren", 100, 100);
		}
		
		player.paintTo(g);
		
		for(Wall wall: walls){
			wall.paintTo(g);
		}
		
		for(Enemy enemy: enemies){
			enemy.paintTo(g);
		}
		lifeBar.paintTo(g);
	}

	@Override
	public void step() {
		player.move();
		
		for(Enemy enemy: enemies){
			enemy.move();
		}
	}

	@Override
	public void checks() {
		checkWallCollisions();
		checkPlayerEnemyCollisions();
		checkForGround();
	}

	//pr�ft, ob eine Figur von einer Plattform f�llt.
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
			if(player.touches(wall)){
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
