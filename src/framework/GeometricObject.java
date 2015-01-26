package framework;

import javax.swing.JComponent;

public abstract class GeometricObject extends JComponent implements MoveAndPaintable {
	public Vertex corner;
	protected double width;
	protected double height;
	public Vertex movement;

	public GeometricObject(Vertex corner, double width, double height, Vertex movement) {
		super();
		this.corner = corner;
		this.width = width;
		this.height = height;
		this.movement = movement;
	}

	public boolean touches(GeometricObject that) {
		return !(isAbove(that) || isUnderneath(that) || isLeftOf(that) || isRightOf(that));
	}
	
	public boolean isAbove(GeometricObject that) {
		return corner.y + height <= that.getCorner().y;
	}

	public boolean isUnderneath(GeometricObject that) {
		return that.isAbove(this);
	}

	public boolean isLeftOf(GeometricObject that) {
		return corner.x + width <= that.getCorner().x;
	}

	public boolean isRightOf(GeometricObject that) {
		return that.isLeftOf(this);
	}
	
	//hat mich das "that"-Object von Osten, oder Westen berührt?
	public boolean isGettingTouchedEastBy(GeometricObject that){
		return touches(that) && corner.x < that.getCorner().x;
	}
	
	public boolean isGettingTouchedWestBy(GeometricObject that){
		return that.isGettingTouchedEastBy(this);
	}
	
	public boolean isStandingOnTopOf(GeometricObject that) {
		return !(isLeftOf(that) || isRightOf(that)) && isAbove(that)
				&& corner.y + height + 5 > that.getCorner().y;
	}

	@Override
	public void move() {
		corner.move(movement);
	}
	
	public void moveTo(Vertex corner){
		this.corner = corner;
	}

	public Vertex getCorner() {
		return corner;
	}
}
