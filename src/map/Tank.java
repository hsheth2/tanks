package map;

import java.awt.Graphics;

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

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
