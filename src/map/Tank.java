package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import main.Window;
import physics.CollisionHandler;
import physics.Vector;

public class Tank extends MovableMapItem {
	public static final Vector SIZE = new Vector(20, 20);
	
	public Tank(Vector position, Vector velocity) {
		super(position, SIZE, velocity);
		// TODO Auto-generated constructor stub
	}
	
	public boolean shoot(Map m, Vector dir) {
		Bullet b = new Bullet(this.position, dir);
		m.addItem(b);
		// TODO rate limiting
		return false;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		
		Point pos = Window.game2real(position);
		Point sz = Window.game2real(size);
		
		g2d.fillRect((int) pos.getX(), (int) pos.getY(), (int) sz.getX(), (int) sz.getY());
	}

	@Override
	public void interact(Map m, CollisionHandler ch) {
		// TODO Auto-generated method stub
		
	}
}
