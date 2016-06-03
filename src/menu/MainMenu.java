package menu;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class MainMenu extends Menu {
	public MainMenu() {
		
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setFont(Menu.fonts.get("Heading"));
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.drawString("TANKS", 100, 100);
	}
}
