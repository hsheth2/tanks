package map;

import java.awt.Rectangle;

import physics.Vector;

public abstract class MapItem implements Drawable {
	protected Vector position;
	protected Vector size;
	
	public MapItem(Vector position, Vector size) {
		this.position = position;
		if (size.getX() < 0 || size.getY() < 0)
			throw new IllegalArgumentException("size can not be negative");
		this.size = size;
	}

	public Vector getPosition() {
		return position;
	}
	
	public void setCenter(Vector center) {
		Vector pos = center.sub(size.multiply(0.5));
		setPosition(pos);
	}

	public void setPosition(Vector position) {
		this.position = position;
	}

	public Vector getSize() {
		return size;
	}
	
	public Vector getCenter() {
		return position.add(size.multiply(0.5));
	}

//	public Vector[] getCorners() {
//		return new Vector[] { position, position.add(size.getI()), position.add(size), position.add(size.getJ()) };
//	}

//	public boolean containsPoint(Vector v) {
//		Vector altCorner = position.add(size);
//		return position.getX() < v.getX() && v.getX() < altCorner.getX() 
//				&& position.getY() < v.getY() && v.getY() < altCorner.getY();
//	}
	
	public Rectangle getRect() {
		return new Rectangle(this.getPosition().intX(), this.getPosition().intY(), this.size.intX(), this.size.intY());
	}
	
	public abstract void hit(MapItem other, Map m);

}
