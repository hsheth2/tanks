package map;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import physics.*;

public class Map implements Drawable {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private ArrayList<MapItem> items = new ArrayList<>();
	private CollisionHandler ch = new CollisionHandler();
	
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
