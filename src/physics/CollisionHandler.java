package physics;

import java.awt.Rectangle;

import map.MapItem;

public class CollisionHandler {
	public CollisionHandler() {
	}
	
	public boolean overlapping(MapItem a, MapItem b) {
		Rectangle one = a.getRect();
		Rectangle two = b.getRect();
		return one.intersects(two);
	}

}
