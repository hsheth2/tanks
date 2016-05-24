package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import main.Window;
import physics.Vector;

public class Bullet extends MovableMapItem {
	public static final Vector SIZE = new Vector(60, 60);
	public static final int SPEED = 12;

	private static final double START_MULT = Bullet.SIZE.add(Tank.SIZE).magnitude() / 2.0;

	public Bullet(Vector position, Vector direction) {
		super(position, SIZE, direction.scale(SPEED));
		this.position = position.add(getVelocity().unit().multiply(START_MULT));
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.ORANGE);

		Point pos = Window.game2real(position);
		Point sz = Window.game2real(size);

		g2d.fillRect((int) pos.getX(), (int) pos.getY(), (int) sz.getX(), (int) sz.getY());
	}

	@Override
	public void hit(MapItem other, Map m) {
		// TODO hit method
	}

}
