package main;

import java.awt.*;

import javax.swing.*;

import physics.Vector;

public class Window extends JFrame {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public static final int GAME_WIDTH = 6400;
	public static final int GAME_HEIGHT = 4800;

	private Game game;

	public static Vector real2game(Point p) {
		return new Vector(GAME_WIDTH * p.getX() / WIDTH, GAME_HEIGHT * p.getY() / HEIGHT);
	}

	public static Point game2real(Vector x) {
		return new Point(WIDTH * x.getX() / GAME_WIDTH, HEIGHT * x.getY() / GAME_HEIGHT);
	}

	public Canvas canvas;

	public Window(Game g) {
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		getContentPane().add(canvas);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setTitle("Tanks Game");
		setVisible(true);

		game = g;
	}

	private class Canvas extends JPanel {
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			
			Rectangle r = new Rectangle(400, 400, 10, 10);
			g2d.setPaint(Color.BLACK);
			g2d.draw(r);

			if (game.menu != null) {
				game.menu.draw(g2d);
			} else {
				game.map.draw(g2d);
			}
		}
	}

}
