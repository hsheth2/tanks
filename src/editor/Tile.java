package editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Tile {
	public char type;
	public int x, y;
	public static final int SIZE = 20;

	public Tile(char type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRect(x * SIZE, y * SIZE, SIZE, SIZE);

		switch (type) {
		case 'w':
			g2d.setColor(Color.LIGHT_GRAY);
			break;
		case 'h':
			g2d.setColor(Color.BLACK);
			break;
		case 'g':
			g2d.setColor(Color.WHITE);
			break;
		}

		g2d.fillRect(x * SIZE, y * SIZE, SIZE, SIZE);
	}
}
