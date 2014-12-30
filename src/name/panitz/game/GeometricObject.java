package name.panitz.game;

import javax.swing.JComponent;

public abstract class GeometricObject extends JComponent implements MoveAndPaintable {
	public Vertex corner;
	double width;
	double height;
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
	
	boolean isAbove(GeometricObject that) {
		return corner.y + height <= that.getCorner().y;
	}

	boolean isUnderneath(GeometricObject that) {
		return that.isAbove(this);
	}

	boolean isLeftOf(GeometricObject that) {
		return corner.x + width <= that.getCorner().x;
	}

	boolean isRightOf(GeometricObject that) {
		return that.isLeftOf(this);
	}
	
	@Override
	public void move() {
		corner.move(movement);
	}

	public boolean isStandingOnTopOf(GeometricObject that) {
		return !(isLeftOf(that) || isRightOf(that)) && isAbove(that)
				&& corner.y + height + 2 > that.getCorner().y;
	}

	public Vertex getCorner() {
		return corner;
	}
}
