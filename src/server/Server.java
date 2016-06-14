package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JTextArea;

import BreezySwing.GBFrame;
import controller.NetworkManager;

public class Server extends GBFrame {
	public static final int MAX_CLIENTS = 4;
	public static final int MIN_CLIENTS = 1;

	private ServerSocket listener;
	private ArrayList<Socket> sockets = new ArrayList<>();

	private JButton start = addButton("Start Game", 1, 1, 1, 1);
	private JButton stop = addButton("Exit", 1, 2, 1, 1);
	private JTextArea log = addTextArea("", 2, 1, 2, 1);

	private Thread accepter;
	private ArrayList<Thread> pool = new ArrayList<>();
	private ArrayList<ThreadServer> servers = new ArrayList<>();

	public static final ArrayList<String> MAPS;
	static {
		File f = new File("assets/levels/");
		MAPS = new ArrayList<String>(Arrays.asList(f.list()));
	}

	private static final int LINE_LIMIT = 500;
	public int mapIndex;

	private volatile boolean running = true;

	private void sysout(String s) {
		sysout(s, true);
	}

	private void error_msg(String s) {
		sysout(s);
		messageBox(s);
	}

	private int lines = 0;

	private void removeLine() {
		String s = log.getText();
		s = s.substring(s.indexOf('\n') + 1);
		log.setText(s);
		lines--;
	}

	private synchronized void sysout(String s, boolean newline) {
		lines++;
		if (lines >= LINE_LIMIT) {
			removeLine();
		}

		if (newline) {
			System.out.println(s);
			log.setText(log.getText() + s + '\n');
		} else {
			System.out.print(s);
			log.setText(log.getText() + s);
		}
	}

	public Server() throws IOException {
		this.setTitle("Tanks Game Server");
		this.setSize(500, 200);

		start.setEnabled(false);
		log.setEditable(false);

		listener = new ServerSocket(NetworkManager.PORT, MAX_CLIENTS);

		sysout("IP: " + InetAddress.getLocalHost());
		sysout("Port: " + listener.getLocalPort());

		pickMap();
		sysout("Map: " + MAPS.get(mapIndex));

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
							if (sockets.size() >= MIN_CLIENTS) {
								start.setEnabled(true);
								start.requestFocus();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						error_msg("error in listener");
						Server.this.running = false;
						Server.this.dispose();
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
					w.write(MAPS.get(mapIndex) + "\n");
					w.flush();
				}

				String line;
				while ((line = r.readLine()) != null) {
					line = line.trim();
					// line = this.id + " " + line;
					sysout("Got message: " + line, false);
					sendOnAll(line);
					sysout(" ... broadcasted");
					if (line.equals(NetworkManager.TERMINATER)) {
						sysout("Termination signal received: stopping");
						new Thread(new Runnable() {
							@Override
							public void run() {
								System.out.println("Attempting to stop the game");
								Server.this.stop();
								System.out.println("GAME SERVER STOPPED");
							}
						}).start();
						
						// busy wait until killed
						while (true) {
							Thread.sleep(10);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				error_msg("error on socket " + id);
				Server.this.running = false;
				Server.this.dispose();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("Crashed while sleeping");
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
				error_msg("error on starting");
				e.printStackTrace();
				Server.this.running = false;
				Server.this.dispose();
			}
		} else if (button == stop) {
			stop();
		}

	}

	@SuppressWarnings("deprecation")
	private void stop() {
		sysout("stopping game");
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

			this.running = false;
			this.setVisible(false);
			this.dispose();
			
			System.out.println("game server stopped");
		} catch (IOException e) {
			e.printStackTrace();
			error_msg("Something went wrong when stopping the server...");
			this.running = false;
			this.dispose();
		}
	}

	private void pickMap() {
		mapIndex = (int) (Math.random() * MAPS.size());
	}

	private static class MainServer extends GBFrame {
		private JButton newGame = addButton("Start a new Game", 1, 1, 1, 1);
		private JButton exit = addButton("Exit", 2, 1, 1, 1);

		public MainServer() {
			this.setTitle("Tanks Server");
			this.setSize(200, 200);

			this.setVisible(true);
		}

		@Override
		public void buttonClicked(JButton button) {
			if (button == newGame) {
				try {
					final Server s = new Server();
					newGame.setEnabled(false);
					exit.setEnabled(false);
					new Thread(new Runnable() {
						@Override
						public void run() {
							Server runner = s;
							while (runner.running == true)
								;
							newGame.setEnabled(true);
							exit.setEnabled(true);
						}
					}).start();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Error in starting game server");
					messageBox("Error in starting game server");
				}
			} else {
				System.out.println("Tanks for playing!");
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) throws Throwable {
		new MainServer();
	}

}
