package menu;

import java.awt.Rectangle;

public abstract class MenuItem {
	public Rectangle box;
	
	public MenuItem(int px, int py, int sx, int sy) {
		box = new Rectangle(px, py, sx, sy);
	}
}
