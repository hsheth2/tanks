package map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import main.Config;
import main.Palette;
import main.Window;
import physics.Vector;

public class Hole extends StaticMapItem {
	public static final Vector SIZE = new Vector(Config.GAME_WIDTH / Config.GRID_WIDTH, Config.GAME_HEIGHT / Config.GRID_HEIGHT);

	public Hole(Vector position) {
		super(position, SIZE);
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Palette.REALLY_BLACK);

		Point pos = Window.game2real(position);
		Point sz = Window.game2real(size);

		g2d.fillRect((int) pos.getX(), (int) pos.getY(), (int) sz.getX(), (int) sz.getY());
		g2d.setColor(Palette.REALLY_BLACK);
		g2d.setStroke(new BasicStroke(4));
		g2d.drawRect((int) pos.getX(), (int) pos.getY(), (int) sz.getX(), (int) sz.getY());
	}

	@Override
	public void hit(MapItem other, Map m) {
		if (other instanceof Wall || other instanceof Hole || other instanceof Mine || other instanceof Bullet) {
			// nothing
		} else if (other instanceof Tank) {
			Tank o = (Tank) other;
			o.unupdate();
			o.setVelocity(o.getVelocity().multiply(MOVE_DIVIDER));
		} else {
			throw new IllegalArgumentException("can't hit " + other.getClass());
		}
	}

}
