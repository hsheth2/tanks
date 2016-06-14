package menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.NetworkManager;
import main.Config;
import main.FontHelper;
import main.Game;
import states.MainMenuState;
import states.NetworkMenuState;
import states.PlayState;

public class NetworkMenu extends Menu {
	private MouseListener ml;
	private KeyListener kl;

	private Label addr, nick;
	private Input addrIn, nickIn;
	private Button connect, back;
	private Label msg;

	public volatile boolean waiting = false;

	public NetworkMenu(final NetworkMenuState state, final JPanel canvas) {
		super(state, canvas);

		this.state = state;
		this.canvas = canvas;

		canvas.setFocusable(true);
		canvas.setBackground(Color.WHITE);

		addr = new Label("Address", 80, 200, Color.BLACK);
		nick = new Label("Nickname", 80, 300, Color.BLACK);

		addrIn = new Input(320, 140, 400, 80);
		nickIn = new Input(320, 240, 400, 80);

		connect = new Button("Connect", MenuItem.centerX(200), 400, 200, 80, Color.LIGHT_GRAY, Color.WHITE);
		back = new Button("Back", 20, 20, 200, 80, Color.LIGHT_GRAY, Color.WHITE);

		g2d.setFont(Label.FONT);
		msg = new Label("Waiting for game to begin...", FontHelper.centerStringX("Waiting for game to begin...", Config.WIDTH, g2d),
				FontHelper.centerStringY("Waiting for game to begin...", Config.HEIGHT, g2d), Color.DARK_GRAY);

		ml = new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				Point p = event.getPoint();
				final Game g = NetworkMenu.this.state.g;

				NetworkMenu.this.canvas.requestFocusInWindow();

				if (back.isHit(p)) {
					g.changeState(new MainMenuState(g));
				} else if (addrIn.isHit(p)) {
					addrIn.focused = true;
					nickIn.focused = false;
				} else if (nickIn.isHit(p)) {
					addrIn.focused = false;
					nickIn.focused = true;
				} else if (connect.isHit(p)) {
					addrIn.focused = false;
					nickIn.focused = false;

					try {
						g.nm = new NetworkManager(state.g, canvas, nickIn.text, addrIn.text, NetworkManager.PORT);
						waiting = true;
						
						new Thread(new Runnable() {
							@Override
							public void run() {
								g.nm.waitForStart();
								state.g.changeState(new PlayState(state.g));
							}
						}).start();
					} catch (UnknownHostException | ConnectException | NoRouteToHostException e) {
						System.out.println("Invalid host address");
						JOptionPane.showMessageDialog(null, "Invalid host address, host is unavailable, or server is not running", "Connection Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					addrIn.focused = false;
					nickIn.focused = false;
				}
			}
		};

		kl = new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				boolean del = false;
				char c = e.getKeyChar();

				if (c == KeyEvent.VK_BACK_SPACE) {
					del = true;
				}

				if (addrIn.focused) {
					if (del) {
						addrIn.backspace();
					} else {
						addrIn.append(c);
					}
				}

				if (nickIn.focused) {
					if (del) {
						nickIn.backspace();
					} else {
						nickIn.append(c);
					}
				}
			}
		};

		canvas.addMouseListener(ml);
		canvas.addKeyListener(kl);
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if (!waiting) {
			back.draw(g2d);
			addr.draw(g2d);
			nick.draw(g2d);
			addrIn.draw(g2d);
			nickIn.draw(g2d);
			connect.draw(g2d);
		} else {
			msg.draw(g2d);
		}
	}

	@Override
	public void cleanup() {
		canvas.removeMouseListener(ml);
		canvas.removeKeyListener(kl);
	}
}
