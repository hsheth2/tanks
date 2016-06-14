package map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import controller.Controller;
import controller.KeyboardController;
import main.AudioPlayer;
import main.Window;
import physics.DeltaTimer;
import physics.Vector;

public class Tank extends MovableMapItem {
	public static final Vector SIZE = new Vector(28, 21);
	public static final int SPEED = 3;

	private static final int MINE_DELAY = DeltaTimer.FPS * 1;
	private static final int SHOOT_DELAY = (int) (DeltaTimer.FPS * 0.5);

	private int lastMineLayed = -2 * MINE_DELAY;

	private String name;
	private Controller control;
	private boolean alive = true;
	private Vector dir = Vector.LEFT;

	public Tank(Vector position, String name) {
		this(position, Vector.ZERO, name);
	}

	public Tank(Vector position, Vector velocity, String name) {
		super(position, SIZE, velocity);
		this.name = name;
	}

	public void setController(Controller c) {
		this.control = c;
	}

	public void actuallyShoot(Map m, Vector dir) {
		this.dir = dir;
		Bullet b = new Bullet(this.getCenter().sub(Bullet.SIZE.multiply(0.5)), dir);
		m.addItem(b);
		AudioPlayer.play("shoot.wav");
	}

	private int lastShot = -2 * SHOOT_DELAY;

	public boolean shoot(Map m, Vector dir) {
		if (lastShot + SHOOT_DELAY <= frameCounter) {
			lastShot = frameCounter;
			actuallyShoot(m, dir);
			return true;
		} else
			return false;
	}
	
	public void actuallyMine(Map m) {
		System.out.println("laying mine");
		Mine mine = new Mine(this.getCenter(), this, m);
		m.addItem(mine);
	}

	public boolean mine(Map m) {
		if (lastMineLayed + MINE_DELAY <= frameCounter) {
			actuallyMine(m);
			lastMineLayed = frameCounter;
			return true;
		} else 
			return false;
	}

	@Override
	public void update() {
		super.update();
		if (control != null && !getVelocity().equals(Vector.ZERO))
			control.locationUpdate();
	};

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Point pos = Window.game2real(position);
		Point sz = Window.game2real(size);
		Point center = Window.game2real(getCenter());

		Path2D.Double path = new Path2D.Double();

		path.append(new Rectangle(pos.x, pos.y, sz.x, sz.y), false);

		AffineTransform at = new AffineTransform();

		at.rotate(Math.toRadians(velocity.angle()), center.x, center.y);
		path.transform(at);
		g2d.setColor(Color.RED);
		g2d.fill(path);

		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(4));
		g2d.draw(path);

		Path2D.Double path2 = new Path2D.Double();
		
		path2.append(new Rectangle(center.x,  center.y - 2, 20, 4), false);
		
		AffineTransform at2 = new AffineTransform();
		
		at2.rotate(Math.toRadians(dir.angle()), center.x, center.y);
		path2.transform(at2);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fill(path2);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));
		g2d.draw(path2);
	}

	public void actuallyDestroy(Map m) {
		if (alive) {
			this.alive = false;
			System.out.println("tank has died");
			AudioPlayer.play("tank_explode.wav");

			if (this.control instanceof KeyboardController)
				((KeyboardController) this.control).died();

			this.control.stop();

			m.removeItem(this);
			m.signalTankDeath();
			// TODO destroy this tank's animation + sound
		}
	}

	public void destroy(Map m) {
		if (this.control instanceof KeyboardController) {
			actuallyDestroy(m);
		}
	}

	@Override
	public void hit(MapItem other, Map m) {
		if (other instanceof Tank) {
			Tank o = (Tank) other;
			this.unupdate();
			o.unupdate();
			this.setVelocity(this.getVelocity().multiply(MOVE_DIVIDER));
			o.setVelocity(o.getVelocity().multiply(MOVE_DIVIDER));
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
