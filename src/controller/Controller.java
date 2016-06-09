package controller;

import map.Map;
import map.Tank;
import physics.Vector;

public abstract class Controller {
	private Map map;
	private Tank tank;

	public Controller(Map map, Tank tank) {
		this.map = map;
		this.tank = tank;
		this.tank.setController(this);
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

	protected boolean shoot(Vector where) {
		// System.out.println("Shoot vec: " + where);
		Vector dir = where.sub(tank.getCenter());
		// System.out.println("Shoot dir: " + dir.unit());

		return tank.shoot(map, dir);
	}

	protected void mine() {
		tank.mine(map);
	}

	public void locationUpdate(Vector loc) {
	}

}
