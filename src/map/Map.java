package map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import main.Window;
import physics.*;

import physics.DeltaTimer;

public class Map implements Drawable {
	private ArrayList<MapItem> items = new ArrayList<>();
	private CollisionHandler ch = new CollisionHandler();

	public final DeltaTimer dt;

	private ArrayList<Integer> removalQueue = new ArrayList<>();

	public Map(DeltaTimer t) {
		this.dt = t;
	}

	public void addItem(MapItem item) {
		items.add(item);
	}

	public boolean removeItem(MapItem item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == item) {
				removalQueue.add(i);
				return true;
			}
		}
		return false;
	}

	private void doRemoval() {
		if (!removalQueue.isEmpty()) {
			System.out.println("Calling do removal");
			for (int idx = removalQueue.size() - 1; idx >= 0; idx--) {
				items.remove((int)removalQueue.get(idx));
			}
			removalQueue = new ArrayList<>();
		}
	}
	
	public void makeRing() {
		int s = Wall.SIZE.intX();
		int w = Window.GAME_WIDTH;
		int h = Window.GAME_HEIGHT;
		
		for (int r = 0; r < w; r += s*4) {
			for (int c = 0; c < h; c += s*4) {
				addItem(new Wall(new Vector(r, 0)));
				addItem(new Wall(new Vector(r, h - s)));
				addItem(new Wall(new Vector(0, c)));
				addItem(new Wall(new Vector(w - s, c)));
			}
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Window.GAME_WIDTH, Window.GAME_HEIGHT);

		for (MapItem item : items) {
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
