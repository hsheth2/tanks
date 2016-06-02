package main;

import java.awt.Graphics2D;

public class MainMenuState extends GameState {
	public MainMenuState(Game g) {
		super(g);
	}

	@Override
	public void init() {
		g.menu = new MainMenu();
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
