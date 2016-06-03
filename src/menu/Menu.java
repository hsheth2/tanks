package menu;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.HashMap;

import main.Config;
import main.Drawable;
import main.FontHelper;

public abstract class Menu implements Drawable {
	public Graphics2D g2d;
	
	public Menu(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	public abstract void cleanup();
}
