package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JPanel;

import map.Map;
import map.Tank;
import physics.Vector;

public class NetworkManager {
	
	private Socket s;
	private BufferedReader r;
	public BufferedWriter w;
	
	private int id;
	private ArrayList<Controller> peers;

	public NetworkManager(Map map, JPanel canvas, String ip, int port) {
		try {
			s = new Socket(ip, port);
			
			r = new BufferedReader(new InputStreamReader(s.getInputStream()));
			w = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			
			// wait for starting signal
			id = Integer.parseInt(r.readLine().trim());
			int peerCount = Integer.parseInt(r.readLine().trim());
			peers = new ArrayList<>(peerCount);
			
			// TODO generate map
			
			// each one will take turns getting a random tank placement
			for (int i = 0; i < peerCount; i++) {
				if (i == id) {
					// my turn
					Vector position = map.getValidPosition(Tank.SIZE);
					Tank me = new Tank(position);
					KeyboardController control_me = new KeyboardController(map, me, canvas);
					map.addItem(me);
					peers.add(control_me);
					sendUpdate(position.getX() + " " + position.getY());
				} else {
					// their turn
					String line = r.readLine().trim();
					String[] tokens = line.split("[\\s]+", 1);
					double x = Double.parseDouble(tokens[0]);
					double y = Double.parseDouble(tokens[1]);
					Vector position = new Vector(x, y);
					Tank them = new Tank(position);
					NetworkedController control_them = new NetworkedController(map, them);
					map.addItem(them);
					peers.add(control_them);
				}
			}
			
			// TODO create new handler thread
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
	
	public void sendUpdate(String data) throws IOException {
		synchronized (s) {
			w.write(id + " " + data + "\n");
			w.flush();
		}
	}

}
