package game;

import framework.ImageObject;
import framework.Vertex;

public class Coin extends ImageObject{
	
	public Coin(Vertex corner){
		super("coin.png", corner, new Vertex(0,0));
	}
}
