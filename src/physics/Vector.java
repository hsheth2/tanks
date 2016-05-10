package physics;

public class Vector {
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
}
