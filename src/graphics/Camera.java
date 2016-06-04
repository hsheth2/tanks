package graphics;

import map.Map;

public class Camera {
	public Plane view;
	public Map map;
	
	public Camera(Plane view, Map map) {
		this.view = view;
		this.map = map;
	}
}
