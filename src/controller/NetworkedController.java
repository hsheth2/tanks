package controller;

import java.io.IOException;
import java.net.Socket;

import map.Map;
import map.Tank;

public class NetworkedController extends Controller {

	public NetworkedController(Map map, Tank tank) {
		super(map, tank);
		
		try {
			Socket s = new Socket("127.0.0.1", 53257);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
