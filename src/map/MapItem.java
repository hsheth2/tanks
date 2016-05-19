package map;

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

	public void setPosition(Vector position) {
		this.position = position;
	}

	public Vector getSize() {
		return size;
	}

	public Vector[] getCorners() {
		return new Vector[] { position, position.add(size.getI()), position.add(size), position.add(size.getJ()) };
	}

	public boolean containsPoint(Vector v) {
		Vector altCorner = position.add(size);
		return position.getX() <= v.getX() && v.getX() <= altCorner.getX() 
				&& position.getY() <= v.getY() && v.getY() <= altCorner.getY();
	}

}
