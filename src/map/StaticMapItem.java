package map;

import main.Drawable;
import physics.Vector;

public abstract class StaticMapItem extends MapItem implements Drawable {

	public StaticMapItem(Vector position, Vector size) {
		super(position, size);
	}

}
