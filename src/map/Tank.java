package map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import controller.Controller;
import controller.KeyboardController;
import main.AudioPlayer;
import main.Config;
import main.FontHelper;
import main.Palette;
import main.Window;
import physics.DeltaTimer;
import physics.Vector;

public class Tank extends MovableMapItem {
	public static final Vector SIZE = new Vector(24, 18);
	public static final int SPEED = 2;

	private static final int MINE_DELAY = DeltaTimer.FPS * 1;
	private static final int SHOOT_DELAY = (int) (DeltaTimer.FPS * 0.3);

	private int lastMineLayed = Config.NO_DEATH_DELAY * DeltaTimer.FPS - MINE_DELAY;
	private int lastShot = Config.NO_DEATH_DELAY * DeltaTimer.FPS - SHOOT_DELAY;

	private String name;
	public Color color;
	private Controller control;
	private boolean alive = true;
	private Vector dir = Vector.LEFT;
	private double facing = 0;

	public Tank(Vector position, String name, Color color) {
		this(position, Vector.ZERO, name);
		this.color = color;
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
		if (control != null && !getVelocity().equals(Vector.ZERO)) {
			control.locationUpdate();
			facing = Math.toRadians(velocity.angle());
		}
	};

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Point pos = Window.game2real(position);
		Point sz = Window.game2real(size);
		Point center = Window.game2real(getCenter());

		Path2D.Double body = new Path2D.Double();

		body.append(new Rectangle(pos.x, pos.y, sz.x, sz.y), false);

		AffineTransform at = new AffineTransform();

		at.rotate(facing, center.x, center.y);
		body.transform(at);
		g2d.setColor(color);
		g2d.fill(body);
		g2d.setColor(Palette.REALLY_BLACK);
		g2d.setStroke(new BasicStroke(4));
		g2d.draw(body);

		AffineTransform at2 = new AffineTransform();

		Path2D.Double barrel = new Path2D.Double();

		sz = Window.game2real(new Vector(12, 4));

		at2.rotate(Math.toRadians(dir.angle()), center.x, center.y);
		barrel.append(new Rectangle(center.x + sz.x / 3, center.y - sz.y / 2, sz.x, sz.y), false);
		barrel.transform(at2);
		g2d.setColor(Palette.LIGHT_GRAY);
		g2d.fill(barrel);
		g2d.setColor(Palette.REALLY_BLACK);
		g2d.setStroke(new BasicStroke(4));
		g2d.draw(barrel);

		Path2D.Double turret = new Path2D.Double();

		sz = Window.game2real(new Vector(12, 12));

		turret.append(new Rectangle(center.x - sz.x / 2, center.y - sz.y / 2, sz.x, sz.x), false);

		turret.transform(at2);
		g2d.setColor(Palette.DARK_GRAY);
		g2d.fill(turret);
		g2d.setColor(Palette.REALLY_BLACK);
		g2d.setStroke(new BasicStroke(4));
		g2d.draw(turret);

		Polygon arrow = new Polygon(new int[] { center.x, center.x - 12, center.x + 12 }, new int[] { center.y - 24, center.y - 36, center.y - 36 }, 3);

		g2d.setColor(color);
		g2d.fillPolygon(arrow);
		g2d.setColor(Palette.REALLY_BLACK);
		g2d.setStroke(new BasicStroke(4));
		g2d.drawPolygon(arrow);
		
		g2d.setColor(Palette.BLACK);
		g2d.setFont(FontHelper.makeFont("RobotoCondensed-Bold.ttf", 24f));
		g2d.drawString(name, center.x - FontHelper.stringWidth(name, g2d) / 2, center.y - 48);
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
