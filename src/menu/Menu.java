package menu;

import java.awt.Graphics2D;

import javax.swing.JPanel;

import main.Drawable;
import main.GameState;

public abstract class Menu implements Drawable {
	public Graphics2D g2d;
	private GameState state;
	private JPanel canvas;
	
	public Menu(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	public abstract void cleanup();
}
