package states;

import main.Game;

public class PlayState extends GameState {
	public PlayState(Game g) {
		super(g);
	}

	public void init() {
		System.out.println("playing");
	}

	@Override
	public void update() {
		if (g.map != null) {
			g.map.update();
		}
	}

	@Override
	public void draw() {
		g.w.validate();
		g.w.repaint();
	}

	@Override
	public void cleanup() {
		g.map = null;
		g.
	}
}
