package main;

import controller.KeyboardController;
import editor.Level;
import map.Map;
import map.Tank;
import physics.Vector;

public class PlayState extends GameState {
	public PlayState(Game g) {
		super(g);
	}

	public void init() {
		boolean dev = true;

		try {
			g.map = new Map(g.dt, Level.load("ring"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Tank t = new Tank(new Vector(400, 400), "me");
		KeyboardController k = new KeyboardController(g.map, t, g.w.canvas);
		g.map.addItem(t);
	}

	@Override
	public void update() {
		if (g.map != null) {
			g.map.update();
		}
	}

	@Override
	public void draw() {
		g.w.repaint();
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}
}
