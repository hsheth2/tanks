package server;

import java.io.Console;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public static final int MAX_CLIENTS = 10;

	private ServerSocket listener;
	private ArrayList<Socket> sockets = new ArrayList<>();

	public Server() throws IOException {
		listener = new ServerSocket(0, MAX_CLIENTS);
		
		System.out.println("IP: " + InetAddress.getLocalHost());
		System.out.println("Listening on " + listener.getLocalPort());
		System.out.println();

		Thread accepter = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						System.out.println("listening for new connections");
						Socket s = listener.accept();
						System.out.println("got a new connection");
						synchronized (sockets) {
							System.out.println("adding to list");
							sockets.add(s);
						}
					} catch (Exception e) {
						System.out.println("error in listener");
						e.printStackTrace();
						System.exit(1);
					}
				}
			}
		});
		accepter.start();

		while (true) {
			// wait for input
			System.out.println("press enter to start");
			
			// windows - read twice
			if ((char)System.in.read() == '\r')
				System.in.read();
			
			int size;
			synchronized (sockets) {
				size = sockets.size();
			}
			
			if (size >= 2)
				break;
			else 
				System.out.println("not enough clients to start a game");
		}

		listener.close();
		
		// TODO function as a server
	}

	public static void main(String[] args) throws Throwable {
		new Server();

	}

}
