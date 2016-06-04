package controller;

import java.io.IOException;
import java.net.Socket;

import map.Map;

public class NetworkManager {

	public NetworkManager(Map map, String ip, int port) {
		try {
			Socket s = new Socket(ip, port);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
