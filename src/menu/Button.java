package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import main.Config;

import map.Drawable;

public class Button extends MenuItem implements Drawable {
	private String text;
	
	public Button(String text, int px, int py, int sx, int sy) {
		super(px, py, sx, sy);
		this.text = text;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(new Color(100, 100, 240));
		g2d.fillRect(center(), box.y, box.width, box.height);
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(Menu.fonts.get("Button"));
		g2d.drawString(text, box.x + 20, box.y + 19);
	}
	
	public boolean isHit(Point p) {
		return box.contains(p);
	}
	
	private int center() {
		return (Config.WIDTH - box.width) / 2;
	}
}
