package main;
import java.awt.Graphics;

import javax.swing.SwingUtilities;

import map.*;
import physics.*;

import map.Map;

public class Game {
	public static Map map = new Map();
	public static Tank tank = new Tank(new Vector(100, 100), new Vector(0, 0));
	private Window w;
	
	
	public Game() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				w = new Window();
			}
		});
	}
	
	public void init() {
		
	}
	
	public void draw() {
		w.repaint();
	}
	
	public void update() {
		map.update();
	}
	
	public static void main(String[] args) {
		Game g = new Game();
		DeltaTimer dt = new DeltaTimer();
		boolean running = true;
		
		g.init();
		
		
		while (running) {
			dt.startIter();
			
			g.update();
			g.draw()
			
			dt.stopIter();
		}
	}
}
