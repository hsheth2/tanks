package controller;

import map.Map;
import map.Tank;
import physics.Vector;

public abstract class Controller {
<<<<<<< HEAD
	public static final int SPEED = 2;
=======
>>>>>>> a7d70fe72fdf51c68170cfb3ff5c563122e80add
	protected Map map;
	protected Tank tank;

	public Controller(Map map, Tank tank) {
		this.map = map;
		this.tank = tank;
	}
	
	protected void setDir(Vector dir, int speed) {
		if (dir.equals(Vector.ZERO))
			tank.setVelocity(dir);
<<<<<<< HEAD
		else tank.setVelocity(dir.scale(speed));
=======
		else tank.setVelocity(dir.scale(Tank.SPEED));
>>>>>>> a7d70fe72fdf51c68170cfb3ff5c563122e80add
	}
	
	protected boolean shoot(Vector where) {		
		System.out.println("Shoot vec: " + where);
		Vector dir = where.sub(tank.getCenter());
		System.out.println("Shoot dir: " + dir.unit());
		
		return tank.shoot(map, dir);
	}

}
