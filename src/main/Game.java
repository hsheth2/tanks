package main;

import javax.swing.SwingUtilities;

import controller.AIController;
import controller.KeyboardController;
import controller.NetworkedController;
import map.Hole;
import map.Map;
import map.Mine;
import map.Tank;
import map.Wall;
import physics.DeltaTimer;
import physics.Vector;

public class Game {
	public Window w;
	public Menu menu;
	public DeltaTimer dt;
	public Map map;
	
	public Game() {
		dt = new DeltaTimer();
		map = new Map(dt);
		
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
				Game.this.w = new Window(Game.this);
//			}
//		});
	}
	
	public void init() {
		map.makeRing();
		
		Tank t = new Tank(new Vector(4000, 4000), Vector.ZERO);
		KeyboardController k = new KeyboardController(this.map, t, this.w.canvas);
		map.addItem(t);
		
		Tank nt = new Tank(new Vector(6000, 2000), Vector.ZERO);
		NetworkedController n = new NetworkedController(this.map, nt);
		
		map.addItem(new Wall(new Vector(3000, 3000)));
		
		Hole h = new Hole(new Vector(500, 400));
		map.addItem(h);
		
		Mine m = new Mine(new Vector(300, 500), t, map);
		map.addItem(m);
		
		Mine m2 = new Mine(new Vector(300, 900), t, map);
		map.addItem(m2);
	}
	
	public void draw() {
		w.validate();
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
