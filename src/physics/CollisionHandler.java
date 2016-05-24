package physics;

import map.MapItem;

public class CollisionHandler {
	public CollisionHandler() {
	}
	
	public boolean overlapping(MapItem a, MapItem b) {
		for (Vector corner : a.getCorners()) {
			if (b.containsPoint(corner))
				return true;
		}
		return false;
	}

}
