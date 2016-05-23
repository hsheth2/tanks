package map;

import physics.Vector;

public abstract class MovableMapItem extends MapItem implements Updatable {
	private Vector velocity;

	public MovableMapItem(Vector position, Vector size, Vector velocity) {
		super(position, size);
		this.velocity = velocity;
	}

	public synchronized void setVelocity(Vector velocity) {
//		//System.out.println("Tank velocity: " + velocity);
		this.velocity = velocity;
	}

	public synchronized Vector getVelocity() {
		return velocity;
	}
	
	@Override
	public void update() {
		this.position = position.add(velocity);
	}
	
}
