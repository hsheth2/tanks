package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.Drawable;
import main.FontHelper;

public class Heading extends MenuItem implements Drawable {
	private String text;
	public static final Font FONT = FontHelper.makeFont("Oswald-Stencil.ttf", 96f);
	public Color color;

	public Heading(String text, int px, int py, Color color) {
		super(px, py, 0, 0);

		this.text = text;
		this.color = color;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setFont(FONT);
		g2d.setColor(color);
		g2d.drawString(text, box.x, box.y);
	}
}
