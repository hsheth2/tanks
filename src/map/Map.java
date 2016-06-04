package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import main.Config;
import main.Drawable;
import physics.CollisionHandler;
import physics.DeltaTimer;
import physics.Vector;

public class Map implements Drawable {
	private ArrayList<MapItem> items = new ArrayList<>();
	private CollisionHandler ch = new CollisionHandler();

	public final DeltaTimer dt;

	private ArrayList<MapItem> removalQueue = new ArrayList<>();

	public Map(DeltaTimer t) {
		this.dt = t;
	}

	public void addItem(MapItem item) {
		if (item == null)
			return;
		items.add(item);
	}

	public boolean removeItem(MapItem item) {
		if (items.contains(item)) {
			if (!removalQueue.contains(item))
				removalQueue.add(item);
		}
		return false;
	}

	public void removeAround(MapItem initial, double radius) {
		ArrayList<MapItem> destroy = new ArrayList<>();
		destroy.add(initial);

		int index = 0;
		while (index < destroy.size()) {
			MapItem item = destroy.get(index++);

			for (int i = 0; i < items.size(); i++) {
				if (items.get(i) instanceof MovableMapItem || items.get(i) instanceof Mine) {
					if (items.get(i).getCenter().sub(item.getCenter()).magnitude() <= radius) {
						if (items.get(i) instanceof Mine && !destroy.contains(items.get(i)))
							destroy.add(items.get(i));
						else {
							if (items.get(i) instanceof Tank) {
								((Tank)items.get(i)).destroy(this);
							} else removeItem(items.get(i));
						}
					}
				}
			}
		}
	}

	private void doRemoval() {
		if (!removalQueue.isEmpty()) {
//			System.out.println("Calling do removal");
			for (MapItem m : removalQueue) {
				int indexOf = items.indexOf(m);
				if (indexOf != -1)
					items.remove(indexOf);
			}
			removalQueue = new ArrayList<>();
		}
	}

	public void makeRing() {
		int s = Wall.SIZE.intX();
		int w = Config.GAME_WIDTH;
		int h = Config.GAME_HEIGHT;

		addItem(new Wall(new Vector(0, 0), new Vector(w, s)));
		addItem(new Wall(new Vector(0, h - s), new Vector(w, s)));
		addItem(new Wall(new Vector(0, 0), new Vector(s, h)));
		addItem(new Wall(new Vector(w - s, 0), new Vector(s, h)));
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Config.GAME_WIDTH, Config.GAME_HEIGHT);

		for (int i = 0; i < items.size(); i++) {
			MapItem item = items.get(i);
			item.draw(g);
		}
	}

	public void update() {
		for (int i = 0; i < items.size(); i++) {
			MapItem item = items.get(i);
			if (item instanceof Updatable) {
				((Updatable) item).update();
			}
		}

		for (int i = 0; i < items.size(); i++) {
			for (int j = i + 1; j < items.size(); j++) {
				// call collision handler
				if (ch.overlapping(items.get(i), items.get(j)))
					items.get(i).hit(items.get(j), this);
			}
		}
		doRemoval();
	}
	
	public Vector getValidPosition(Vector size) {
		Vector position;
		do {
			int x = ThreadLocalRandom.current().nextInt(0, Config.GAME_WIDTH);
			int y = ThreadLocalRandom.current().nextInt(0, Config.GAME_HEIGHT);
			position = new Vector(x, y);
		} while (!isEmpty(position, size));
		return position;
	}
	
	public boolean isEmpty(Vector pos, Vector size) {
		MapItem input = new Wall(pos, size);
		for (MapItem item : items) {
			if (ch.overlapping(item, input))
				return false;
		}
		return true;
	}
}
