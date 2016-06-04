package graphics;

public class Plane {
	public Vector3 a, b;
	
	public Plane(Vector3 a, Vector3 b) {
		this.a = a;
		this.b = b;
	}
	
	public Vector3 normal() {
		return a.cross(b).unit();
	}
}
