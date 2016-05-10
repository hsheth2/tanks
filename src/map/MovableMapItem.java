package map;

import physics.Vector;

public abstract class MovableMapItem extends MapItem implements Updatable {
	protected Vector velocity;

	public MovableMapItem(Vector position, Vector size, Vector velocity) {
		super(position, size);
		this.velocity = velocity;
	}

	

}
