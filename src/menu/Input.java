package menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.Drawable;
import main.FontHelper;

public class Input extends MenuItem implements Drawable {
	public static final Font FONT = FontHelper.makeFont("RobotoCondensed-Regular.ttf", 48f);
	public boolean focused;
	public String text;
	public static final int MAX_LEN = 15;

	public Input(int px, int py, int sx, int sy) {
		super(px, py, sx, sy);

		focused = false;
		text = "";
	}

	public void backspace() {
		text = (text.length() == 0) ? "" : text.substring(0, text.length() - 1);
	}

	public void append(char c) {
		if (c >= 32 && c < 127) {
			if (text.length() < MAX_LEN) {
				text += c;
			}
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (focused) {
			g2d.setColor(Color.BLACK);
		} else {
			g2d.setColor(Color.GRAY);
		}

		g2d.setStroke(new BasicStroke(4));
		g2d.drawRect(box.x, box.y, box.width, box.height);

		g2d.setColor(Color.BLACK);
		g2d.setFont(FONT);
		g2d.drawString(text, box.x + 20, box.y + FontHelper.centerStringY(text, box.height, g2d));
	}
}
