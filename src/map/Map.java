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
		for (int idx = removalQueue.size()-1; idx >= 0; idx--) {
			items.remove(removalQueue.get(idx));
		}
		removalQueue = new ArrayList<>();
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Window.GAME_WIDTH, Window.GAME_HEIGHT);
		
		for (MapItem item : items) {
			item.draw(g);
		}
	}
	
	public void update() {
		for (MapItem item : items) {
			if (item instanceof Updatable) {
				((Updatable) item).update();
			}
		}
		
		for (int i = 0; i < items.size(); i++) {
			for (int j = i+1; j < items.size(); j++) {
				// call collision handler
				if (ch.overlapping(items.get(i), items.get(j)))
					items.get(i).hit(items.get(j), this);
			}
		}
		doRemoval();
	}
}
