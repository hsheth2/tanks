package controller;

import map.Map;
import map.Tank;
import physics.Vector;

public abstract class Controller {
	public static final int SPEED = 2;
	protected Map map;
	protected Tank tank;

	public Controller(Map map, Tank tank) {
		this.map = map;
		this.tank = tank;
	}
	
	protected void setDir(Vector dir, int speed) {
		if (dir.equals(Vector.ZERO))
			tank.setVelocity(dir);
		else tank.setVelocity(dir.scale(speed));
	}
	
	protected boolean shoot(Vector where) {		
		System.out.println("Shoot vec: " + where);
		Vector dir = where.sub(tank.getCenter());
		System.out.println("Shoot dir: " + dir.unit());
		
		return tank.shoot(map, dir);
	}

}
