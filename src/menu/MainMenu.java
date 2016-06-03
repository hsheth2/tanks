package menu;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class MainMenu extends Menu {
	Button b;
	
	public MainMenu(JPanel canvas) {
		b = new Button("hello", 400, 400, 80, 20);
		
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (b.isHit(e.getPoint())) {
					System.out.println("button clicked");
				}
			}
		});
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setFont(Menu.fonts.get("Heading"));
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.drawString("TANKS", 100, 100);
		
		b.draw(g2d);
	}
}
