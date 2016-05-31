package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import main.Window;
import physics.Vector;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import physics.DeltaTimer;

public class Mine extends StaticMapItem implements Updatable {
	public static final Vector SIZE = new Vector(100, 100);

	public static final int TIMER_END = 5 * DeltaTimer.FPS;

	private static final double MINE_RADIUS = 500;

	private int timer = 0;
	private Tank owner;
	
	private Map map;

	public Mine(Vector position, Tank owner) {
		super(position, SIZE);
		this.owner = owner;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.MAGENTA);

		Point pos = Window.game2real(position);
		Point sz = Window.game2real(size);

		g2d.fillRect((int) pos.getX(), (int) pos.getY(), (int) sz.getX(), (int) sz.getY());
	}
	
	public void destroy(Map m) {
		m.removeAround(this, MINE_RADIUS);
		// TODO destroy this mine's animation + sound
	}

	@Override
	public void update() {
		timer++;
		if (timer >= TIMER_END) {
			this.destroy(map);
		}
	}

	@Override
	public void unupdate() {
		throw new NotImplementedException();
	}

	@Override
	public void hit(MapItem other, Map m) {
		this.map = m;
		if (other instanceof Wall || other instanceof Hole || other instanceof Mine) {
			// nothing
		} else if (other instanceof Tank) {
			Tank o = (Tank) other;
			if (o != this.owner) { // ignore parent Tank
				this.destroy(m);
			}
		} else if (other instanceof Bullet) {
			Bullet b = (Bullet) other;
			
			b.destroy(m);
			this.destroy(m);
		} else {
			throw new IllegalArgumentException("can't hit " + other.getClass());
		}
	}

}
