package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import main.Window;
import physics.CollisionHandler;
import physics.Vector;

public class Mine extends StaticMapItem implements Updatable {
	public static final Vector SIZE = new Vector(30, 30);
	
	public Mine(Vector position) {
		super(position, SIZE);
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.MAGENTA);
		
		Point pos = Window.game2real(position);
		Point sz = Window.game2real(size);
		
		g2d.fillRect((int) pos.getX(), (int) pos.getY(), (int) sz.getX(), (int) sz.getY());
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
