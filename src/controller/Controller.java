package controller;

import map.Map;
import map.Tank;
import physics.Vector;

public abstract class Controller {
	protected Map map;
	protected Tank tank;

	protected boolean running = true;

	public Controller(Map map, Tank tank) {
		this.map = map;
		this.tank = tank;
		this.tank.setController(this);
	}

	public boolean isRunning() {
		return running;
	}

	protected void setDir(Vector dir) {
		if (dir.equals(Vector.ZERO))
			tank.setVelocity(dir);
		else
			tank.setVelocity(dir.scale(Tank.SPEED));
	}

	protected void setLoc(Vector loc) {
		tank.setPosition(loc);
	}

	public void stop() {
		if (this.running) {
			this.running = false;
		}
	}

	public void locationUpdate() {
	}
}
