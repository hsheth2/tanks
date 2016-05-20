package main;
import java.awt.Graphics;

import javax.swing.SwingUtilities;

import map.*;
import physics.*;

import map.Map;

public class Game {
	public Window w;
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
		map.addItem(new Tank(new Vector(10, 10), new Vector(2, 2)));
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
