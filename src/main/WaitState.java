package main;

import menu.WaitMenu;

public class WaitState extends GameState {
	public WaitState(Game g) {
		super(g);
	}

	@Override
	public void init() {
		g.menu = new WaitMenu(this, g.w.canvas);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}
}
