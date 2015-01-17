package framework;

public interface GameFramework extends Paintable {

	void step();

	void checks();

	Player getPlayer();

	int getHeight();

	int getWidth();

	boolean lost();

	boolean won();

	default boolean finished() {
		return won() || lost();
	}

	void spawnEnemy();
}
