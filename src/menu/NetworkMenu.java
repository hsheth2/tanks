package menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.NetworkManager;
import controller.NetworkingException;
import main.Game;
import main.NetworkMenuState;
import main.PlayState;

public class NetworkMenu extends Menu {
	private NetworkMenuState state;
	private JPanel canvas;
	private MouseListener ml;
	private KeyListener kl;

	private Label addr, nick;
	private Input addrIn, nickIn;
	private Button connect;

	public NetworkMenu(final NetworkMenuState state, final JPanel canvas) {
		super((Graphics2D) canvas.getGraphics());

		this.state = state;
		this.canvas = canvas;

		canvas.setFocusable(true);
		canvas.setBackground(Color.WHITE);

		addr = new Label("Address", 100, 200, Color.BLACK);
		nick = new Label("Nickname", 100, 300, Color.BLACK);

		addrIn = new Input(400, 140, 300, 80);
		nickIn = new Input(400, 240, 300, 80);

		connect = new Button("Connect", MenuItem.centerX(200), 400, 200, 80, Color.BLUE, Color.WHITE);

		ml = new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				Point p = event.getPoint();
				Game g = NetworkMenu.this.state.g;

				NetworkMenu.this.canvas.requestFocusInWindow();

				if (addrIn.isHit(p)) {
					addrIn.focused = true;
					nickIn.focused = false;
				} else if (nickIn.isHit(p)) {
					addrIn.focused = false;
					nickIn.focused = true;
				} else if (connect.isHit(p)) {
					addrIn.focused = false;
					nickIn.focused = false;

					NetworkManager nm;

					try {
						nm = new NetworkManager(state.g, canvas, nickIn.text, addrIn.text, NetworkManager.PORT);
						nm.waitForStart();

						while (g.map == null)
							;

						state.g.changeState(new PlayState(state.g));
					} catch (UnknownHostException | ConnectException e) {
						System.out.println("Invalid host address");
						JOptionPane.showMessageDialog(null, "Invalid host address");
					} catch (NetworkingException e) {
						e.printStackTrace();
						System.exit(1); // FIXME fail gracefully
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

				if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
					del = true;
				}

				if (addrIn.focused) {
					if (del) {
						addrIn.backspace();
					} else {
						addrIn.text += e.getKeyChar();
					}
				}

				if (nickIn.focused) {
					if (del) {
						nickIn.backspace();
					} else {
						nickIn.text += e.getKeyChar();
					}
				}
			}
		};

		canvas.addMouseListener(ml);
		canvas.addKeyListener(kl);
	}

	@Override
	public void draw(Graphics2D g2d) {
		addr.draw(g2d);
		nick.draw(g2d);
		addrIn.draw(g2d);
		nickIn.draw(g2d);
		connect.draw(g2d);
	}

	@Override
	public void cleanup() {
		canvas.removeMouseListener(ml);
		canvas.removeKeyListener(kl);
	}
}
