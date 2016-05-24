package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import main.Window;
import physics.Vector;

public class Wall extends StaticMapItem {
	public static final Vector SIZE = new Vector(40, 40);

	public Wall(Vector position) {
		super(position, SIZE);
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.LIGHT_GRAY);
		
		Point pos = Window.game2real(position);
		Point sz = Window.game2real(size);
		
		g2d.fillRect((int) pos.getX(), (int) pos.getY(), (int) sz.getX(), (int) sz.getY());
	}

	@Override
	public void hit(MapItem other, Map m) {
		if (other instanceof Wall || other instanceof Hole) {
			// nothing
		} else if (other instanceof Tank) {
			Tank o = (Tank) other;
			Vector push = pushDirection(o, this);
			o.setPosition(o.getPosition().add(push));
			o.setVelocity(Vector.ZERO);
		}
		// TODO hit method
	}

}
