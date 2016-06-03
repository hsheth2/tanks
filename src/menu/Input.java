package menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import main.Drawable;

public class Input extends MenuItem implements Drawable {
	public boolean focused;
	
	public Input(int px, int py, int sx, int sy) {
		super(px, py, sx, sy);
		
		focused = false;
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (focused) {
			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(6));
		} else {
			g2d.setColor(Color.GRAY);
			g2d.setStroke(new BasicStroke(4));
		}
		
		g2d.drawRect(box.x, box.y, box.width, box.height);
	}
}
