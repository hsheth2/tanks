package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import main.AudioPlayer;
import main.Window;
import physics.Vector;

public class Bullet extends MovableMapItem {
	public static final Vector SIZE = new Vector(8, 8);
	public static final int SPEED = 5;

	private static final double START_MULT = Bullet.SIZE.add(Tank.SIZE).magnitude() / 2.0;
	private static final int BOUNCE_LIMIT = 1;

	int bounceCount = 0;

	public Bullet(Vector position, Vector direction) {
		super(position, SIZE, direction.scale(SPEED));
		this.position = position.add(getVelocity().unit().multiply(START_MULT));
	}

	@Override
	public void draw(Graphics2D g2d) {
		Point pos = Window.game2real(position);
		Point sz = Window.game2real(size);
		Point center = Window.game2real(getCenter());
		
		Path2D.Double path = new Path2D.Double();
		
		path.append(new Rectangle(pos.x, pos.y, sz.x, sz.y), false);
		
		AffineTransform at = new AffineTransform();
		
		at.rotate(Math.toRadians(velocity.angle()), center.x, center.y);
		path.transform(at);
		g2d.setColor(Color.ORANGE);
		g2d.fill(path);
	}

	public void destroy(Map m) {
		m.removeItem(this);
		// TODO destroy this bullet animation + sound
	}

	@Override
	public void hit(MapItem other, Map m) {
		if (other instanceof Hole) {
			// nothing
		} else if (other instanceof Bullet) {
			Bullet b = (Bullet) other;
			this.destroy(m);
			b.destroy(m);
		} else if (other instanceof Wall) {
			if (bounceCount < BOUNCE_LIMIT) {
				this.unupdate();
				this.bounceOff(other);
				bounceCount++;
				AudioPlayer.play("bounce.wav");
			} else {
				this.destroy(m);
			}
		} else if (other instanceof Tank) {
			Tank t = (Tank) other;

			this.destroy(m);
			t.destroy(m);
		} else if (other instanceof Mine) {
			other.hit(this, m);
		} else {
			throw new IllegalArgumentException("can't hit " + other.getClass());
		}
	}

}
