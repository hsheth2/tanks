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
	public static final int PORT = 6840;

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

			// each one will take turns getting a random tank placement
			for (int i = 0; i < peerCount; i++) {
				if (i == id) {
					// my turn
					Vector position = map.getValidPosition(Tank.SIZE);
					Tank me = new Tank(position);
					KeyboardController control_me = new KeyboardController(map, me, canvas);
					map.addItem(me);
					peers.add(control_me);
					sendUpdate(position.toComputerString());
					r.readLine(); // read my own send
				} else {
					// their turn
					String line = r.readLine();
					Vector position = Vector.parseLine(line);
					Tank them = new Tank(position);
					NetworkedController control_them = new NetworkedController(map, them);
					map.addItem(them);
					peers.add(control_them);
				}
			}

			// send start message and wait to sync
			if (this.id == 0) {
				sendUpdate("STARTING\n");
			}
			r.readLine();

			// start server thread
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							String[] lineTokens = r.readLine().trim().split("[\\s]+", 1);
							int peerId = Integer.parseInt(lineTokens[0]);
							String cmd = lineTokens[1];

							if (peerId != NetworkManager.this.id) {
								// not me
								NetworkedController ctl = (NetworkedController) peers.get(peerId);
								ctl.handleAction(cmd);
							} else {
								// ignore my own sends
							}
						} catch (IOException e) {
							e.printStackTrace();
							System.exit(1);
						} catch (Exception e) {
							e.printStackTrace();
							System.exit(1);
						}
					}
				}
			}).start();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void sendUpdate(String data) {
		synchronized (s) {
			try {
				w.write(id + " " + data + "\n");
				w.flush();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("failed to send on socket");
				System.exit(1);
			}
		}
	}

}
