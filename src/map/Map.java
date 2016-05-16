package map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Map implements Drawable {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private ArrayList<MapItem> items = new ArrayList<>();
	
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

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
}
