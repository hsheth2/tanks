package physics;

public class Vector {
	public static final Vector ZERO = new Vector(0, 0);
	public static final Vector UP = new Vector(0, 1);
	public static final Vector DOWN = new Vector(0, -1);
	public static final Vector LEFT = new Vector(-1, 0);
	public static final Vector RIGHT = new Vector(1, 0);
	
	private double x, y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public Vector add(Vector v) {
		return new Vector(x + v.getX(), y + v.getY());
	}
	
	public double magnitude() {
		return Math.sqrt(x*x + y*y);
	}
	
	public Vector unit() {
		double mag = magnitude();
		
		return new Vector(x/mag, y/mag);
	}
	
	public Vector scale(int mag) {
		Vector unit = unit();
		
		return new Vector(mag*unit.getX(), mag*unit.getY());
	}
	
	public boolean equals(Vector o) {
		return x == o.x && y == o.y;
	}
	
	public String toString() {
		return String.format("Vector(%f, %f)", x, y);
	}
}
