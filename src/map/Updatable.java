package map;

import physics.CollisionHandler;

public interface Updatable {
	public void interact(Map m, CollisionHandler ch);
	public void update();
}
