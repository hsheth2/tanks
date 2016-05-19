package map;

import java.awt.Graphics2D;

import physics.Vector;

public class Wall extends StaticMapItem {
	public static final Vector SIZE = new Vector(40, 40);

	public Wall(Vector position) {
		super(position, SIZE);
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

}
