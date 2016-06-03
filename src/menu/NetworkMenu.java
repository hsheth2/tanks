package menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import main.Config;
import main.FontHelper;
import main.Game;
import main.NetworkMenuState;
import main.PlayState;

public class NetworkMenu extends Menu {
	private NetworkMenuState state;
	private JPanel canvas;
	private MouseListener ml;
	
	private Label addr, nick;
	private Input addrIn, nickIn;
	private Button connect;
	
	public NetworkMenu(NetworkMenuState state, JPanel canvas) {
		super((Graphics2D) canvas.getGraphics());
		
		this.state = state;
		this.canvas = canvas;
		
		canvas.setBackground(Color.WHITE);
		
		addr = new Label("Address", 100, 200, Color.BLACK);
		nick = new Label("Nickname", 100, 300, Color.BLACK);
		
		addrIn = new Input(400, 140, 300, 80);
		nickIn = new Input(400, 240, 300, 80);
		
		connect = new Button("Connect", MenuItem.centerX(200), 400, 200, 80, Color.BLUE, Color.WHITE);
		
		ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				Game g = NetworkMenu.this.state.g;
				
				if (addrIn.isHit(p)) {
					addrIn.focused = true;
					nickIn.focused = false;
				} else if (nickIn.isHit(p)) {
					addrIn.focused = false;
					nickIn.focused = true;
				} else if (connect.isHit(p)) {
					System.out.println("connect");
				} else {
					addrIn.focused = false;
					nickIn.focused = false;
				}
			}
		};
		
		canvas.addMouseListener(ml);
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
	}

}
