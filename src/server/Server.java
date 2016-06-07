package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextArea;

import BreezySwing.GBFrame;
import controller.NetworkManager;

public class Server extends GBFrame {
	public static final int MAX_CLIENTS = 10;
	public static final int MIN_CLIENTS = 2;

	private ServerSocket listener;
	private ArrayList<Socket> sockets = new ArrayList<>();

	private JButton start = addButton("Start Game", 1, 1, 1, 1);
	private JButton stop = addButton("Exit", 1, 2, 1, 1);
	private JTextArea log = addTextArea("", 2, 1, 2, 1);

	private Thread accepter;
	private ArrayList<Thread> pool = new ArrayList<>();
	private ArrayList<ThreadServer> servers = new ArrayList<>();

	public static final String[] MAPS = { "test", "ring", "circles" };
	public int mapIndex;

	private void sysout(String s) {
		sysout(s, true);
	}

	private void sysout(String s, boolean newline) {
		if (newline) {
			System.out.println(s);
			log.setText(log.getText() + s + '\n');
		} else {
			System.out.print(s);
			log.setText(log.getText() + s);
		}
	}

	public Server() throws IOException {
		this.setTitle("Tanks Server");
		this.setSize(400, 200);

		start.setEnabled(false);
		log.setEditable(false);

		listener = new ServerSocket(NetworkManager.PORT, MAX_CLIENTS);

		sysout("IP: " + InetAddress.getLocalHost());
		sysout("Port: " + listener.getLocalPort());

		pickMap();

		accepter = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						sysout("listening for new connections");
						Socket s = listener.accept();
						sysout("got a new connection");
						synchronized (sockets) {
							// System.out.println("adding to list");
							sockets.add(s);
							sysout(sockets.size() + " clients connected");
							if (sockets.size() >= MIN_CLIENTS)
								start.setEnabled(true);
						}
					} catch (Exception e) {
						sysout("error in listener");
						e.printStackTrace();
						System.exit(1);
					}
				}
			}
		});
		accepter.start();

		this.setVisible(true);
	}

	private void sendOnAll(String text) throws IOException {
		synchronized (servers) {
			for (ThreadServer s : servers) {
				s.w.write(text + "\n");
				s.w.flush();
			}
		}
	}

	private class ThreadServer implements Runnable {
		private int id;
		private Socket s;

		private BufferedReader r;
		public BufferedWriter w;

		public ThreadServer(int id, Socket s) throws IOException {
			this.id = id;
			this.s = s;

			r = new BufferedReader(new InputStreamReader(s.getInputStream()));
			w = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
		}

		@Override
		public void run() {
			try {
				synchronized (servers) {
					w.write(id + "\n");
					w.write(servers.size() + "\n");
					w.write(MAPS[mapIndex] + "\n");
					w.flush();
				}

				String line;
				while ((line = r.readLine()) != null) {
					line = line.trim();
					// line = this.id + " " + line;
					sysout("Got message: " + line);
					sendOnAll(line);
				}
			} catch (IOException e) {
				sysout("error on socket " + id);
				e.printStackTrace();
				System.exit(1); // FIXME fail gracefully
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void buttonClicked(JButton button) {
		if (button == start) {
			try {
				accepter.stop();
				accepter = null;
				listener.close();
				listener = null;

				sysout("Starting game... ", false);

				for (int i = 0; i < sockets.size(); i++) {
					ThreadServer t = new ThreadServer(i, sockets.get(i));
					servers.add(t);
					pool.add(new Thread(t));
				}
				for (int i = 0; i < pool.size(); i++) {
					pool.get(i).start();
				}

				start.setEnabled(false);
				sysout("started");
			} catch (IOException e) {
				sysout("error on starting");
				e.printStackTrace();
				System.exit(1);
			}
		} else if (button == stop) {
			try {
				if (accepter != null) {
					accepter.stop();
					accepter = null;
				}
				
				if (listener != null) {
					listener.close();
					listener = null;
				}

				for (Thread x : pool) {
					x.stop();
				}

				for (ThreadServer x : servers) {
					x.r.close();
					x.r = null;
					x.w.close();
					x.w = null;
					x.s.close();
					x.s = null;
				}

				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1); // FIXME fail gracefully
			}
		}

	}

	private void pickMap() {
		mapIndex = (int) (Math.random() * MAPS.length);
	}

	public static void main(String[] args) throws Throwable {
		new Server();
	}

}
