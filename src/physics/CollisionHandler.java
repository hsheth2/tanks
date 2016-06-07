package physics;

import java.awt.Rectangle;

import map.MapItem;

public class CollisionHandler {
	public boolean overlapping(MapItem a, MapItem b) {
		try {
			Rectangle one = a.getRect();
			Rectangle two = b.getRect();
			return one.intersects(two);
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("non-fatal error - continue game");
			return false;
		}
	}

}
