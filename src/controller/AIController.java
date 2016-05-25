package controller;

import map.Map;
import map.Tank;

public class AIController extends Controller {
	private Tank enemy;
	
	public AIController(Map map, Tank tank, Tank enemy) {
		super(map, tank);
		
		this.enemy = enemy;
	}
	
	public void act() {
		setDir(enemy.getPosition().sub(tank.getPosition()), 1);
	}
}
