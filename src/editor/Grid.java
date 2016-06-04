package editor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class Grid extends JPanel {
	public Tile[][] tiles;
	public MouseListener ml;
	public MouseMotionListener mml;
	public boolean drag = false;
	
	public Grid(int w, int h) {
		tiles = new Tile[w][h];
		
		init();
		
		ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				handle(e);
			}
		};
		
		mml = new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				handle(e);
			}
		};
		
		addMouseListener(ml);
		addMouseMotionListener(mml);
	}
	
	public void init() {
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				tiles[x][y] = new Tile('g', x, y);
			}
		}
	}
	
	public void handle(MouseEvent e) {
		Point p = e.getPoint();
		int x = p.x / Tile.SIZE;
		int y = p.y / Tile.SIZE;
		
		if (x < tiles.length && y < tiles[0].length) {
			tiles[p.x / Tile.SIZE][p.y / Tile.SIZE].type = 'w';
			repaint();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				tiles[x][y].draw(g2d);
			}
		}
	}
}
