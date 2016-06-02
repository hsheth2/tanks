package main;

import java.awt.Graphics2D;

public abstract class GameState {
	public Game g;
	
	public GameState(Game g) {
		this.g = g;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void draw();
}
