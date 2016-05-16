package map;

import java.awt.Graphics;

import physics.Vector;

public class Wall extends StaticMapItem {
	public static final Vector SIZE = new Vector(40, 40);

	public Wall(Vector position) {
		super(position, SIZE);
		// TODO Auto-generated constructor stub
	}

}
