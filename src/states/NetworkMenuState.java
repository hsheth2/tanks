package states;

import main.Game;
import menu.NetworkMenu;

public class NetworkMenuState extends GameState {
	public NetworkMenuState(Game g) {
		super(g);
	}

	@Override
	public void init() {
		g.menu = new NetworkMenu(this, g.w.canvas);
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
