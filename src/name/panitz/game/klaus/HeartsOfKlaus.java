package name.panitz.game.klaus;

import java.awt.Graphics;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;

import name.panitz.game.*;
import name.panitz.game.util.FileUtil;

public class HeartsOfKlaus implements GameFramework {

	Klaus player;
	List<Wall> walls = new ArrayList<>();
	List<Heart> hearts = new ArrayList<>();
	List<Barrel> barrels = new ArrayList<>();

	int width = 800;
	int height = 600;

	int energy = 5;
	int collectedHearts = 0;

	public HeartsOfKlaus() throws Exception {
		loadLevel("./images/level1.txt");
	}

	void loadLevel(String levelDescription) {

		String[] lines = FileUtil.readTextLines(getClass().getClassLoader()
				.getResourceAsStream(levelDescription));

		int l = 0;
		for (String ln : lines) {
			int col = 0;
			for (char c : ln.toCharArray()) {
				switch (c) {
				case 'w':
					walls.add(new Wall(new Vertex(col * 40, l * 40)));
					break;
				case 'h':
					hearts.add(new Heart(new Vertex(col * 40, l * 40)));
					break;
				case 'f':
					barrels.add(new Barrel(new Vertex(col * 40, l * 40)));
					break;
				case 'p':
					player = new Klaus(new Vertex(col * 40, l * 40));
					break;
				}
				col++;
			}
			width = col * 40;
			l++;
		}
		height = l * 40;
	}

	public void step() {
		player.move();
		for (Moveable p : barrels)
			p.move();
	}

	public void paintTo(Graphics g) {
		for (Paintable p : walls)
			p.paintTo(g);
		for (Paintable p : hearts)
			p.paintTo(g);
		for (Paintable p : barrels)
			p.paintTo(g);
		player.paintTo(g);

		g.drawString("Energy: " + energy, 50, 10);
		g.drawString("Hearts: " + hearts.size(), 50, 30);
	}

	public void checks() {
		collectHearts();
		checkPlayerWallCollsions();
		fallingBarrel();
		playerBarrelCollision();

		if (getPlayer().getCorner().y > height) {
			getPlayer().getCorner().moveTo(new Vertex(40, height - 80));
		}
	}

	private void collectHearts() {
		Heart removeMe = null;
		for (Heart heart : hearts) {
			if (player.touches(heart)) {
				removeMe = heart;
				collectedHearts++;
				break;
			}
		}
		if (removeMe != null)
			hearts.remove(removeMe);
	}

	private void playerBarrelCollision() {
		for (Barrel p : barrels) {
			if (p.touches(player)) {
				energy--;
				playSound("./sounds/crash.wav");
				p.fromTop(width);
			}
			if (p.getCorner().y > height) {
				p.fromTop(width);
			}
		}
	}

	private void fallingBarrel() {
		for (Barrel p : barrels) {
			boolean isStandingOnTop = false;
			for (GeometricObject wall : walls) {
				if (p.touches(wall)) {
					p.restart();
				}
				if (p.isStandingOnTopOf(wall)) {
					isStandingOnTop = true;
				}
			}
			if (!isStandingOnTop && !p.isJumping) {
				p.startJump(0.1);
			}
		}
	}

	private void checkPlayerWallCollsions() {
		boolean isStandingOnTop = false;
		for (GeometricObject wall : walls) {
			if (player.touches(wall)) {
				player.stop();
				return;
			}
			if (player.isStandingOnTopOf(wall)) {
				isStandingOnTop = true;
			}
		}

		if (!isStandingOnTop && !player.isJumping)
			player.startJump(0.1);
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

	@Override
	public boolean lost() {
		return energy <= 0;
	}

	@Override
	public boolean won() {
		return hearts.isEmpty();
	}

	void playSound(String soundFile) {
		try {
			InputStream soundStream = getClass().getClassLoader()
					.getResourceAsStream(soundFile);

			AudioInputStream stream = AudioSystem
					.getAudioInputStream(soundStream);

			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void spawnEnemy() {
		// TODO Auto-generated method stub
		
	}
}
