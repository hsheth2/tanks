package map;

import physics.Vector;

public abstract class MovableMapItem extends MapItem implements Updatable {
	private Vector velocity;

	public MovableMapItem(Vector position, Vector size, Vector velocity) {
		super(position, size);
		this.velocity = velocity;
	}

	public synchronized void setVelocity(Vector velocity) {
		//System.out.println("new velocity: " + velocity);
		this.velocity = velocity;
	}

	public synchronized Vector getVelocity() {
		return velocity;
	}
	
	public void bounceOff(MapItem other) {
		Vector dir = other.getCenter().sub(this.getCenter());
		
		// find angle
		double angle = dir.angle();
		
//		System.out.println("Bounce Angle: " + angle);
		Vector v = this.getVelocity();
//		System.out.println("Current vel " + v);
		if (45 < angle && angle < 135) {
			// negate y
			this.setVelocity(new Vector(v.getX(), -v.getY()));
		} else if (135 < angle && angle < 225) {
			// negate x
			this.setVelocity(new Vector(-v.getX(), v.getY()));
		} else if (225 < angle && angle < 315) {
			// negate y
			this.setVelocity(new Vector(v.getX(), -v.getY()));
		} else if (0 < angle && angle < 45 || 315 < angle && angle < 360) {
			// negate x
			this.setVelocity(new Vector(-v.getX(), v.getY()));
		} else {
			// straight onto corner
			// negate both x and y
			this.setVelocity(new Vector(-v.getX(), -v.getY()));
		}
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
