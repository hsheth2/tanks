package controller;

import map.Map;
import map.Tank;
import physics.Vector;

public class NetworkedController extends Controller {

	public NetworkedController(Map map, Tank tank) {
		super(map, tank);
	}

	public void handleAction(String action) {
		String[] input = action.split("[\\s]+", 2);
		if (input[0].equals("shoot")) {
			Vector where = Vector.parseLine(input[1]);
			this.shoot(where);
		} else if (input[0].equals("move")) {
			Vector dir = Vector.parseLine(input[1]);
			setDir(dir);
		} else if (input[0].equals("loc")) {
			Vector loc = Vector.parseLine(input[1]);
			this.setLoc(loc);
		} else if (input[0].equals("mine")) {
			mine();
		} else if (input[0].equals("die")) {
			this.tank.actuallyDestroy(this.map);
		} else {
			throw new IllegalArgumentException("can not respond to " + input[0] + " action");
		}
	}

}
