package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import main.Window;
import physics.CollisionHandler;
import physics.Vector;

public class Bullet extends MovableMapItem {
	public static final Vector SIZE = new Vector(10, 10);
	public static final int SPEED = 10;

	public Bullet(Vector position, Vector direction) {
		super(position, SIZE, direction.scale(SPEED));
		this.position = position.add(velocity.multiply(2));
	}

	@Override
	public void interact(Map m, CollisionHandler ch) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.ORANGE);
		
		Point pos = Window.game2real(position);
		Point sz = Window.game2real(size);
		
		g2d.fillRect((int) pos.getX(), (int) pos.getY(), (int) sz.getX(), (int) sz.getY());
	}

	
}
