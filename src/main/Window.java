package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.*;

import physics.Vector;

public class Window extends JFrame {
	private Game game;

	public static Vector real2game(Point p) {
		return new Vector(Config.GAME_WIDTH * p.getX() / Config.WIDTH, Config.GAME_HEIGHT * p.getY() / Config.HEIGHT);
	}

	public static Point game2real(Vector x) {
		return new Point( (int)(Config.WIDTH * x.getX() / Config.GAME_WIDTH), (int)(Config.HEIGHT * x.getY() / Config.GAME_HEIGHT));
	}

	public Canvas canvas;

	public Window(Game g) {
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(Config.WIDTH, Config.HEIGHT));

		getContentPane().add(canvas);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setTitle("Tanks Game");
		setVisible(true);
		setResizable(true);

		game = g;
	}

	private class Canvas extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			if (game.state instanceof PlayState && game.map != null) {
				game.map.draw(g2d);
			} else if ((game.state instanceof MainMenuState || game.state instanceof NetworkMenuState) && game.menu != null) {
				game.menu.draw(g2d);
			}
		}
	}
}
