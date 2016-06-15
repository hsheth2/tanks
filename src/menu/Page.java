package menu;

import java.awt.Graphics2D;

import main.Config;
import main.Drawable;
import main.FontHelper;
import main.Palette;

public class Page extends MenuItem implements Drawable {
	public Menu parent;
	public Text[] lines;
	public String title;

	public Page(String title, String[] lines, int px, int py, Menu parent) {
		super(px, py, 0, 0);

		this.title = title;
		this.parent = parent;
		this.lines = new Text[lines.length];

		parent.g2d.setFont(Text.FONT);

		int y = py;
		int yinc = FontHelper.stringHeight(parent.g2d);

		for (int i = 0; i < lines.length; i++) {
			this.lines[i] = new Text(lines[i], FontHelper.centerStringX(lines[i], Config.WIDTH, parent.g2d), y, Palette.BLACK);

			y += yinc;
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		for (Text line : lines) {
			line.draw(g2d);
		}
	}
}
