package map;

import java.awt.Graphics2D;

import physics.Vector;

public class Hole extends StaticMapItem {

	/**
	 * 
	 */
	public Hole() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void hit(MapItem other) {
		if (other instanceof Wall || other instanceof Hole) {
			// nothing
		} else if (other instanceof Tank) {
			Tank o = (Tank) other;
			o.setVelocity(Vector.ZERO);
		}
		// TODO hit method
	}

}
