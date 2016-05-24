package physics;

import map.MapItem;

public class CollisionHandler {
	public CollisionHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean overlapping(MapItem a, MapItem b) {
		for (Vector corner : a.getCorners()) {
			if (b.containsPoint(corner))
				return true;
		}
		return false;
	}

}
