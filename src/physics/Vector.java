package physics;

public class Vector {
	public static final Vector ZERO = new Vector(0, 0);
	public static final Vector UP = new Vector(0, -1);
	public static final Vector DOWN = new Vector(0, 1);
	public static final Vector LEFT = new Vector(-1, 0);
	public static final Vector RIGHT = new Vector(1, 0);

	private final double x, y;

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public static Vector parseLine(String line) {
		line = line.trim();
		String[] tokens = line.split("X", 1);
		double x = Double.parseDouble(tokens[0]);
		double y = Double.parseDouble(tokens[1]);
		Vector ans = new Vector(x, y);
		return ans;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int intX() {
		return (int) x;
	}

	public int intY() {
		return (int) y;
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
		return Math.sqrt(x * x + y * y);
	}

	public Vector unit() {
		double mag = magnitude();

		return new Vector(x / mag, y / mag);
	}

	public Vector multiply(double times) {
		return new Vector(times * x, times * y);
	}

	public Vector scale(double mag) {
		return this.unit().multiply(mag);
	}

	public double angle() {
		double angle = Math.toDegrees(Math.atan2(y, x));
		return (360.0 + angle) % 360.0;
	}

	public boolean equals(Vector o) {
		return x == o.x && y == o.y;
	}

	public Vector nearestInt() {
		return new Vector((int) Math.round(x), (int) Math.round(y));
	}

	public String toString() {
		return String.format("Vector(%f, %f)", x, y);
	}

	public String toComputerString() {
		return String.format("%fX%f", x, y);
	}
}
