package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JPanel;

import map.Map;

public class NetworkManager {
	
	private Socket s;
	private BufferedReader r;
	public BufferedWriter w;
	private int id;

	public NetworkManager(Map map, JPanel canvas, String ip, int port) {
		try {
			s = new Socket(ip, port);
			
			r = new BufferedReader(new InputStreamReader(s.getInputStream()));
			w = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			
			// wait for starting signal
			id = Integer.parseInt(r.readLine().trim());
			int peerCount = Integer.parseInt(r.readLine().trim());
			
			// TODO create all NetworkedControllers and KeyboardControllers
			
			// TODO create new handler thread
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
	
	public void sendUpdate(String data) throws IOException {
		synchronized (s) {
			w.write(data + "\n");
			w.flush();
		}
	}

}
