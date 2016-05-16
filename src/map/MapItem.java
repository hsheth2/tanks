package map;

import physics.Vector;

public abstract class MapItem implements Drawable {
	protected Vector position;
	protected Vector size;

	public MapItem(Vector position, Vector size) {
		this.position = position;
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

}
