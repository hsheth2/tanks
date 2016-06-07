package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JPanel;

import editor.Level;
import main.Game;
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

	public NetworkManager(Game g, JPanel canvas, String nickname, String ip, int port) {
		try {
			s = new Socket(ip, port);

			r = new BufferedReader(new InputStreamReader(s.getInputStream()));
			w = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

			// wait for starting signal
			id = Integer.parseInt(r.readLine().trim());
			int peerCount = Integer.parseInt(r.readLine().trim());
			peers = new ArrayList<>(peerCount);

			// get map
			String mapName = r.readLine().trim();
			g.map = new Map(g.dt, Level.load(mapName));

			// each one will take turns getting a random tank placement
			for (int i = 0; i < peerCount; i++) {
				if (i == id) {
					// my turn
					Vector position = g.map.getValidPosition(Tank.SIZE);
					Tank me = new Tank(position, nickname);
					KeyboardController control_me = new KeyboardController(g.map, me, canvas, this);
					g.map.addItem(me);
					peers.add(control_me);
					sendUpdate(position.toComputerString() + " " + nickname);
					r.readLine(); // read my own send
				} else {
					// their turn
					String[] line = r.readLine().trim().split("[\\s]+");
					Vector position = Vector.parseLine(line[1]);
					Tank them = new Tank(position, line[2]);
					NetworkedController control_them = new NetworkedController(g.map, them);
					g.map.addItem(them);
					peers.add(control_them);
				}
			}

			// send start message and wait to sync
			if (this.id == 0) {
				sendUpdate("STARTING");
			}
			r.readLine();

			// start server thread
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							String[] lineTokens = r.readLine().trim().split("[\\s]+", 2);
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
