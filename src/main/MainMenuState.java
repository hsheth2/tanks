package main;

import menu.MainMenu;

public class MainMenuState extends GameState {
	public MainMenuState(Game g) {
		super(g);
	}

	@Override
	public void init() {
		g.menu = new MainMenu(g.w.canvas);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		g.w.validate();
		g.w.repaint();
	}
}
