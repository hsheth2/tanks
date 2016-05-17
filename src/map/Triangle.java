package map;

import java.awt.Color;
import java.awt.Graphics2D;

import physics.Vector;

public class Triangle implements Drawable {
	private Vector[] points;
	
	public Triangle(String s) {
		String[] pts = s.split(" ");
		
		for (int i = 0; i < 3; i++) {
			points[i] = Double.parseDouble(pts[i]);
		}
	}
	
	private int[] unpackX() {
		int[] xs = new int[3];
		
		for (int i = 0; i < 3; i++) {
			xs[i] = (int) points[i].getX();
		}
		
		return xs;
	}
	
	private int[] unpackY() {
		int[] ys = new int[3];
		
		for (int i = 0; i < 3; i++) {
			ys[i] = (int) points[i].getY();
		}
		
		return ys;
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		g2d.drawPolygon(unpackX(), unpackY(), 3);
	}
	
	public String toString() {
		return String.format("Triangle[%s, %s, %s)]\n", points[0], points[1], points[2]);
	}
}
