package map;

import java.awt.Color;
import java.awt.Graphics2D;

import physics.CollisionHandler;
import physics.Vector;

public class Bullet extends MovableMapItem {
	public static final Vector SIZE = new Vector(10, 10);

	public Bullet(Vector position, Vector velocity) {
		super(position, SIZE, velocity);
		// TODO Auto-generated constructor stub
	}

	public void update(Map m, CollisionHandler ch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.ORANGE);
		g2d.fillRect(position.getX(), position.getY(), SIZE.getX(), SIZE.getY());
	}
}
