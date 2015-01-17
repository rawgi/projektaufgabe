package framework;

import java.awt.event.KeyListener;

public interface Player {

	KeyListener getKeyListener();

	Vertex getCorner();
}