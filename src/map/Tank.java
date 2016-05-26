package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import main.Window;
import physics.Vector;

public class Tank extends MovableMapItem {
	public static final Vector SIZE = new Vector(160, 160);
	public static int SPEED = 3;

	public Tank(Vector position, Vector velocity) {
		super(position, SIZE, velocity);
	}

	public boolean shoot(Map m, Vector dir) {
		Bullet b = new Bullet(this.getCenter().sub(Bullet.SIZE.multiply(0.5)), dir);
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
	
	public void destroy(Map m) {
		System.out.println("tank has died");
		m.removeItem(this);
		// TODO destroy this tank's animation + sound
		// TODO stop game if this is me
		System.exit(1);
	}

	@Override
	public void hit(MapItem other, Map m) {
		if (other instanceof Tank) {
			Tank o = (Tank) other;
			this.unupdate();
			o.unupdate();
			this.setVelocity(Vector.ZERO);
			o.setVelocity(Vector.ZERO);
		} else if (other instanceof Wall || other instanceof Hole) {
			other.hit(this, m);
		} else if (other instanceof Mine) {
			other.hit(this, m);
		} else if (other instanceof Bullet) {
			other.hit(this, m);
		} else {
			throw new IllegalArgumentException("can't hit " + other.getClass());
		}
	}
}
