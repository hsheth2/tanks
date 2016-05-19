package main;
import java.awt.*;

import javax.swing.*;

import controller.KeyboardController;
import map.Map;
import map.Tank;
import physics.Vector;

public class Window extends JFrame {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public static final int GAME_WIDTH = 1600;
	public static final int GAME_HEIGHT = 1200;
	
	private Graphics2D g2d;
	
	public static Vector real2game(Point p) {
		return new Vector(GAME_WIDTH * p.getX() / WIDTH, GAME_HEIGHT * p.getY() / HEIGHT);
	}
	
	public static Point game2real(Vector x) {
		return new Point(WIDTH * x.getX() / GAME_WIDTH, HEIGHT * x.getY() / GAME_HEIGHT);
	}

	private Canvas canvas;

	public Window() {
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		getContentPane().add(canvas);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setTitle("Tanks Game");
		setVisible(true);

		// FIXME remove this testing code
		Map m = new Map();
		Tank t = new Tank(new Vector(500, 500), Vector.ZERO);
		m.addItem(t);
		KeyboardController k = new KeyboardController(m, t, canvas);
	}

	private class Canvas extends JPanel {
		public void paintComponent(Graphics g) {
			Game.map.draw(g2d);
		}
	}
}
