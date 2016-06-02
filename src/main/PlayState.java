package main;

import java.awt.Graphics2D;

import controller.KeyboardController;
import map.*;
import physics.Vector;

public class PlayState extends GameState {
	public PlayState(Game g) {
		super(g);
	}

	public void init() {
		Map map = g.map;
		Window w = g.w;
		
		map.makeRing();
		
		Tank t = new Tank(new Vector(4000, 4000), Vector.ZERO);
		KeyboardController k = new KeyboardController(map, t, w.canvas);
		map.addItem(t);
		
		map.addItem(new Wall(new Vector(3000, 3000)));
		
		Hole h = new Hole(new Vector(500, 400));
		map.addItem(h);
		
		Mine m = new Mine(new Vector(300, 500), t);
		map.addItem(m);
		m.hit(h, map);
	}

	@Override
	public void update() {
		g.map.update();
	}

	@Override
	public void draw() {
		g.w.validate();
		g.w.repaint();
	}
}
