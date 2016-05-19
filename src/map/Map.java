package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import physics.*;

import physics.DeltaTimer;

public class Map implements Drawable {
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 1200;
	
	private ArrayList<MapItem> items = new ArrayList<>();
	private CollisionHandler ch = new CollisionHandler();
	
	public final DeltaTimer dt;
	
	public Map(DeltaTimer t) {
		this.dt = t;
	}
	
	public void addItem(MapItem item) {
		items.add(item);
	}
	
	public boolean removeItem(MapItem item) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) == item) {
				items.remove(i);
				return true;
			}
		}
		return false;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for (MapItem item : items) {
			item.draw(g);
		}
	}
	
	public void update() {
		for (MapItem item : items) {
			if (item instanceof Updatable) {
				((Updatable) item).update(this, ch);
			}
		}
	}
}
