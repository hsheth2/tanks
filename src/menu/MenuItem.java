package menu;

import java.awt.Point;
import java.awt.Rectangle;

import main.Config;

public abstract class MenuItem {
	public Rectangle box;
	
	public MenuItem(int px, int py, int sx, int sy) {
		box = new Rectangle(px, py, sx, sy);
	}
	
	public boolean isHit(Point p) {
		return box.contains(p);
	}

	public static int centerX(int w) {
		return (Config.WIDTH - w) / 2;
	}
	
	public static int centerY(int h) {
		return (Config.HEIGHT - h) / 2;
	}
}
