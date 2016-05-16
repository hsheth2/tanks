package map;

import java.awt.Color;
import java.awt.Graphics;

public class Map implements Drawable {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	public Map() {
		
	}

	public void draw(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}
}
