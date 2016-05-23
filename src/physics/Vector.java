package physics;

public class Vector {
	public static final Vector ZERO = new Vector(0, 0);
	public static final Vector UP = new Vector(0, -1);
	public static final Vector DOWN = new Vector(0, 1);
	public static final Vector LEFT = new Vector(-1, 0);
	public static final Vector RIGHT = new Vector(1, 0);
	
	private double x, y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public Vector getI() {
		return new Vector(x, 0);
	}
	
	public Vector getJ() {
		return new Vector(0, y);
	}
	
	public Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y);
	}
	
	public Vector sub(Vector v) {
		return this.add(v.multiply(-1));
	}
	
	public double magnitude() {
		return Math.sqrt(x*x + y*y);
	}
	
	public Vector unit() {
		double mag = magnitude();
		
		return new Vector(x/mag, y/mag);
	}
	
	public Vector multiply(double times) {
		return new Vector(times*x, times*y);
	}
	
	public Vector scale(double mag) {
		return this.unit().multiply(mag);
	}
	
	public boolean equals(Vector o) {
		return x == o.x && y == o.y;
	}
	
	public String toString() {
		return String.format("Vector(%f, %f)", x, y);
	}
}
