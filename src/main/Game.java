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
import menu.Menu;
import physics.DeltaTimer;
import physics.Vector;

public class Game {
	public volatile Window w;
	public GameState state;
	public DeltaTimer dt;
	public Map map;
	public Menu menu;
	
	public Game() {
		dt = new DeltaTimer();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Game.this.w = new Window(Game.this);
			}
		});
	}
	
	public void changeState(GameState state) {
		if (this.state != null) {
			this.state.cleanup();
		}
		
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
