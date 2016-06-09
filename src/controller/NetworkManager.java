package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JPanel;

import editor.Level;
import main.Game;
import map.Map;
import map.Tank;
import physics.Vector;

public class NetworkManager {
	public static final int PORT = 6840;

	private Game g;
	private JPanel canvas;
	private String nickname;
	private Socket s;
	private BufferedReader r;
	private BufferedWriter w;

	private int id;
	private ArrayList<Controller> peers;
	private Thread serverThread;

	public NetworkManager(Game g, JPanel canvas, String nickname, String ip, int port)
			throws UnknownHostException, ConnectException, NetworkingException {
		try {
			this.s = new Socket(ip, port);

			this.r = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.w = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

			this.g = g;
			this.canvas = canvas;
			this.nickname = nickname;
		} catch (UnknownHostException | ConnectException e) {
			throw e;
		} catch (IOException e) {
			throw new NetworkingException(e);
		}
	}

	public void waitForStart() {
		try {
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
			serverThread = new Thread(new Runnable() {
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
							throw new NetworkingException(e);
						}
					}
				}
			});
			serverThread.start();
		} catch (IOException e) {
			throw new NetworkingException("Something went wrong with server or socket", e);
		}
	}

	public void sendUpdate(String data) throws NetworkingException {
		synchronized (s) {
			try {
				w.write(id + " " + data + "\n");
				w.flush();
			} catch (IOException e) {
				throw new NetworkingException("failed to send update on socket", e);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		try {
			serverThread.stop();
			serverThread = null;

			r.close();
			r = null;
			w.close();
			w = null;

			s.close();
			s = null;
			
			for (Controller c : peers)
				c.stop();
		} catch (IOException e) {
			throw new NetworkingException("failed to close network manager properly", e);
		}
	}

}
