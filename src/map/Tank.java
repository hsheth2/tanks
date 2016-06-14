package map;

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
	public static final Vector SIZE = new Vector(16, 16);
	public static final int SPEED = 3;

	private static final int MINE_DELAY = DeltaTimer.FPS * 1;

	private int lastMineLayed = -10000;

	private String name;
	private Controller control;
	private boolean alive = true;

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

	public boolean shoot(Map m, Vector dir) {
		Bullet b = new Bullet(this.getCenter().sub(Bullet.SIZE.multiply(0.5)), dir);
		m.addItem(b);
		AudioPlayer.play("shoot.wav");
		// TODO rate limiting
		return false;
	}

	public void mine(Map m) {
		if (lastMineLayed + MINE_DELAY <= frameCounter) {
			System.out.println("laying mine");
			Mine mine = new Mine(this.getCenter(), this, m);
			m.addItem(mine);
			lastMineLayed = frameCounter;
		}
	}

	@Override
	public void update() {
		super.update();
		if (control != null)
			control.locationUpdate(this.getPosition());
	};

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.RED);

		Point pos = Window.game2real(position);
		Point sz = Window.game2real(size);

		Path2D.Double path = new Path2D.Double();
		
		path.append(new Rectangle(pos.x, pos.y, sz.x, sz.y), false);
		
		AffineTransform at = new AffineTransform();
		
//		at.rotate(45);
		path.transform(at);
		g2d.draw(path);
		g2d.setColor(Color.BLACK);
		g2d.fill(path);
//		g2d.fillRect((int) pos.getX(), (int) pos.getY(), (int) sz.getX(), (int) sz.getY());
//		g2d.setColor(Color.BLACK);
//		g2d.drawString(name, (int) pos.getX(), (int) pos.getY());
	}
	
	public void actuallyDestroy(Map m) {
		if (alive) {
			this.alive = false;
			System.out.println("tank has died");

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
