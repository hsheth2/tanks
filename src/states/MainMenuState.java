package states;

import main.Game;
import menu.MainMenu;

public class MainMenuState extends GameState {
	public MainMenuState(Game g) {
		super(g);
	}

	@Override
	public void init() {
		g.menu = new MainMenu(this, g.w.canvas);
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
