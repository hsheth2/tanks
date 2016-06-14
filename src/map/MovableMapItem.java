package map;

import physics.Vector;

public abstract class MovableMapItem extends MapItem implements Updatable {
	protected Vector velocity;

	protected int frameCounter = 0;

	public MovableMapItem(Vector position, Vector size, Vector velocity) {
		super(position, size);
		this.velocity = velocity;
	}

	public synchronized void setVelocity(Vector velocity) {
		// System.out.println("new velocity: " + velocity);
		this.velocity = velocity;
	}

	public synchronized Vector getVelocity() {
		return velocity;
	}

	public void bounceOff(MapItem other) {
		Vector dir = other.getCenter().sub(this.getCenter());

		// find angle
		double angle = dir.angle();

		// find threshold
		double thresh = other.size.angle();
		// System.out.println(thresh);

		// System.out.println("Bounce Angle: " + angle);
		Vector v = this.getVelocity();
		// System.out.println("Current vel " + v);
		if (thresh < angle && angle < 180 - thresh) {
			// negate y
			this.setVelocity(new Vector(v.getX(), -v.getY()));
		} else if (180 - thresh < angle && angle < 180 + thresh) {
			// negate x
			this.setVelocity(new Vector(-v.getX(), v.getY()));
		} else if (180 + thresh < angle && angle < 360 - thresh) {
			// negate y
			this.setVelocity(new Vector(v.getX(), -v.getY()));
		} else if (0 < angle && angle < thresh || 360 - thresh < angle && angle < 360) {
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
		frameCounter++;
		this.position = position.add(velocity);
	}

	@Override
	public void unupdate() {
		this.position = position.sub(velocity);
	}

}
