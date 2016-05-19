package controller;

import map.Tank;
import physics.Vector;

public abstract class Controller {
	private static final int SPEED = 2;
	
	protected Tank tank;

	public Controller(Tank tank) {
		this.tank = tank;
	}
	
	protected void setDir(Vector dir) {
		if (dir.equals(Vector.ZERO))
			tank.setVelocity(dir);
		else tank.setVelocity(dir.unit().scale(SPEED));
	}
	
	protected boolean shoot(Vector where) {		
		System.out.println("Shoot vec: " + where);
		Vector dir = where.sub(tank.getPosition());
		System.out.println("Shoot dir: " + dir);
		
		return tank.shoot(dir);
	}

}
