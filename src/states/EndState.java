package states;

import main.Game;
import menu.EndMenu;

public class EndState extends GameState {
	public EndState(Game g) {
		super(g);
	}

	@Override
	public void init() {
		g.menu = new EndMenu(this, g.w.canvas);
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
