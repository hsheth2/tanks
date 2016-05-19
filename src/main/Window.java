package main;
import java.awt.*;

import javax.swing.*;

import controller.KeyboardController;
import map.Tank;
import physics.Vector;

public class Window extends JFrame {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public static final int GAME_WIDTH = 1600;
	public static final int GAME_HEIGHT = 1200;
	
	public static Vector real2game(Point p) {
		return new Vector(GAME_WIDTH * p.getX() / WIDTH, GAME_HEIGHT * p.getY() / HEIGHT);
	}
	
	public static Point game2real(Vector x) {
		// TODO this method
		return null;
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
		Tank t = new Tank(new Vector(500, 500), Vector.ZERO);
		KeyboardController k = new KeyboardController(t, canvas);
	}

	private class Canvas extends JPanel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;

			Game.map.draw(g2d);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Window();
			}
		});
	}
}