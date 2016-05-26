package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import main.Window;
import physics.*;

import physics.DeltaTimer;

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
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == item) {
				removalQueue.add(items.get(i));
				return true;
			}
		}
		return false;
	}

	private void doRemoval() {
		if (!removalQueue.isEmpty()) {
			System.out.println("Calling do removal");
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
		int w = Window.GAME_WIDTH;
		int h = Window.GAME_HEIGHT;

		addItem(new Wall(new Vector(0, 0), new Vector(w, s)));
		addItem(new Wall(new Vector(0, h - s), new Vector(w, s)));
		addItem(new Wall(new Vector(0, 0), new Vector(s, h)));
		addItem(new Wall(new Vector(w - s, 0), new Vector(s, h)));
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Window.GAME_WIDTH, Window.GAME_HEIGHT);

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
}
