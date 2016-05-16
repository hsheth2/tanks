package map;

import physics.Vector;

public abstract class MovableMapItem extends MapItem implements Updatable {
	protected Vector velocity;

	public MovableMapItem(Vector position, Vector size, Vector velocity) {
		super(position, size);
		this.velocity = velocity;
	}

	public void setVelocity(Vector velocity) {
		System.out.println("Tank velocity: " + velocity);
		this.velocity = velocity;
	}

}
