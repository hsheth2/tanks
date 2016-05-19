package map;

import java.awt.Graphics2D;

import physics.CollisionHandler;
import physics.Vector;

public class Tank extends MovableMapItem {
	public static final Vector SIZE = new Vector(20, 20);
	
	public Tank(Vector position, Vector velocity) {
		super(position, SIZE, velocity);
		// TODO Auto-generated constructor stub
	}

	public void update(Map m, CollisionHandler ch) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean shoot(Vector dir) {
		// TODO rate limiting
		// TODO shooting mechanics
		// TODO set bullet speed
		return false;
	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		
	}

}
