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
	
	public void bounceOff(MapItem other) {
		Vector dir = this.getCenter().sub(other.getCenter());
		
		// find angle
		double angle = dir.angle();
		
		// TODO finish bounce
	}
	
	@Override
	public void update() {
		this.position = position.add(velocity);
	}
	
	@Override
	public void unupdate() {
		this.position = position.sub(velocity);
	}
	
}
