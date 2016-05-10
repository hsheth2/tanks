package map;

import physics.Vector;

public abstract class MapItem implements Drawable {
	protected Vector position;
	protected Vector size;

	public MapItem(Vector position, Vector size) {
		this.position = position;
		this.size = size;
	}

}
