package main;

import javax.swing.SwingUtilities;

import controller.KeyboardController;
import map.*;
import physics.*;

import map.Map;

public class Game {
	public Window w;
	public Menu menu;
	public DeltaTimer dt;
	public Map map;
	
	public Game() {
		dt = new DeltaTimer();
		map = new Map(dt);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Game.this.w = new Window(Game.this);
			}
		});
	}
	
	public void init() {
		Tank t = new Tank(new Vector(3000, 3000), Vector.ZERO);
		KeyboardController k = new KeyboardController(this.map, t, this.w.canvas);
		map.addItem(t);
		
		Wall w = new Wall(new Vector(100, 200));
		map.addItem(w);
		
		Wall w2 = new Wall(new Vector(3000, 200));
		map.addItem(w2);
		
		Hole h = new Hole(new Vector(500, 400));
		map.addItem(h);
		
		Mine m = new Mine(new Vector(300, 500), t);
		map.addItem(m);
	}
	
	public void draw() {
		w.repaint();
	}
	
	public void update() {
		map.update();
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		boolean running = true;
		
		while (g.w == null);
		
		g.init();
		
		while (running) {
			g.dt.startIter();
			
			g.update();
			g.draw();
			
			g.dt.stopIter();
		}
	}
}
