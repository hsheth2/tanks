package menu;

import java.awt.Graphics2D;

import javax.swing.JPanel;

import main.Drawable;
import states.GameState;

public abstract class Menu implements Drawable {
	public GameState state;
	public JPanel canvas;
	public Graphics2D g2d;

	public Menu(GameState state, JPanel canvas) {
		this.state = state;
		this.canvas = canvas;
		g2d = (Graphics2D) canvas.getGraphics();
	}

	public abstract void cleanup();
}
