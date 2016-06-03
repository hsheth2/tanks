package menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import main.Config;
import main.Drawable;
import main.FontHelper;

public class Button extends MenuItem implements Drawable {
	private String text;
	public static final Font FONT = FontHelper.makeFont("Bebas.ttf", 30f);
	public Color fill, tc;
	
	public Button(String text, int px, int py, int sx, int sy, Color fill, Color tc) {
		super(px, py, sx, sy);
		
		this.text = text;
		this.fill = fill;
		this.tc = tc;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(fill);
		g2d.fillRect(box.x, box.y, box.width, box.height);
		
		g2d.setFont(FONT);
		g2d.setColor(tc);
		g2d.drawString(text, box.x + FontHelper.centerStringX(text, box.width, FONT, g2d), box.y + FontHelper.centerStringY(box.height, FONT, g2d));
	}
}
