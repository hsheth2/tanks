package graphics;

public class Vector3 {
	public final double x, y, z;
	
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3 add(Vector3 v) {
		return new Vector3(x + v.x, y + v.y, z + v.z);
	}
	
	public Vector3 sub(Vector3 v) {
		return this.add(v.mult(-1));
	}
	
	public Vector3 mult(double s) {
		return new Vector3(s*x, s*y, s*z);
	}
	
	public double magnitude() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public Vector3 unit() {
		double mag = magnitude();
		
		return new Vector3(x/mag, y/mag, z/mag);
	}
	
	public Vector3 scale(double mag) {
		return this.unit().mult(mag);
	}
	
	public double dot(Vector3 v) {
		return x*v.x + y*v.y + z*v.z;
	}
	
	public Vector3 cross(Vector3 v) {
		return new Vector3(y*v.z - z*v.y, z*v.x - x*v.z, x*v.y - y*v.x);
	}
	
	public String toString() {
		return String.format("Vector3(%f, %f, %f)", x, y, z);
	}
}
