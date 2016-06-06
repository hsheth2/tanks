package controller;

import map.Map;
import map.Tank;
import physics.Vector;

public class NetworkedController extends Controller {

	public NetworkedController(Map map, Tank tank) {
		super(map, tank);
	}

	public void handleAction(String action) {
		String[] input = action.split("[\\s]+", 1);
		if (input[0].equals("shoot")) {
			Vector where = Vector.parseLine(input[1]);
			this.tank.shoot(this.map, where);
		} else if (input[0].equals("move")) {
			Vector dir = Vector.parseLine(input[1]);
			setDir(dir);
		} else if (input[0].equals("mine")) {
			mine();
		} else {
			throw new IllegalArgumentException("can not respond to " + input[0] + " action");
		}
	}

}
