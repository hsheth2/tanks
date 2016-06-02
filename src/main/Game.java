package main;

import javax.swing.SwingUtilities;

import controller.AIController;
import controller.KeyboardController;
import map.Hole;
import map.Map;
import map.Mine;
import map.Tank;
import map.Wall;
import physics.DeltaTimer;
import physics.Vector;

public class Game {
	public Window w;
	public GameState state;
	public DeltaTimer dt;
	public Map map;
	public Menu menu;
	
	public Game() {
		dt = new DeltaTimer();
		map = new Map(dt);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Game.this.w = new Window(Game.this);
			}
		});
	}
	
	public void changeState(GameState state) {
		this.state = state;
		state.init();
	}
	
	public void draw() {
		state.draw();
	}
	
	public void update() {
		state.update();
	}
	
	public static void main(String[] args) throws Throwable {
		Game g = new Game();
		boolean running = true;
		
		while (g.w == null);
		
		g.changeState(new MainMenuState(g));
		
		while (running) {
			g.dt.startIter();
			
			g.update();
			g.draw();
			
			g.dt.stopIter();
		}
	}
}
