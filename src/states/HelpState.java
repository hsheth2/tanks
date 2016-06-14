package states;

import main.Game;
import menu.HelpMenu;

public class HelpState extends GameState {
	public HelpState(Game g) {
		super(g);
	}

	@Override
	public void init() {
		g.menu = new HelpMenu(this, g.w.canvas);
	}

	@Override
	public void update() {

	}

	@Override
	public void draw() {
		g.w.validate();
		g.w.repaint();
	}

	@Override
	public void cleanup() {
		g.menu.cleanup();
		g.menu = null;
	}
}
