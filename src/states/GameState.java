package states;

import main.Game;

public abstract class GameState {
	public Game g;

	public GameState(Game g) {
		this.g = g;
	}

	public abstract void init();

	public abstract void update();

	public abstract void draw();

	public abstract void cleanup();
}
