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
import main.MainMenuState;
import main.NetworkMenuState;
import main.PlayState;

public class MainMenu extends Menu {
	private MainMenuState state;
	private JPanel canvas;
	private MouseListener ml;
	
	private Heading title;
	private Button solo, multi;
	
	public MainMenu(MainMenuState state, JPanel canvas) {
		super((Graphics2D) canvas.getGraphics());
		
		this.state = state;
		this.canvas = canvas;
		
		canvas.setBackground(Color.WHITE);
		
		title = new Heading("TANKS", FontHelper.centerStringX("TANKS", Config.WIDTH, Heading.FONT, g2d), 200, Color.BLACK);
		solo = new Button("Solo", MenuItem.centerX(200), 250, 200, 80, Color.blue, Color.WHITE);
		multi = new Button("Multiplayer", MenuItem.centerX(200), 350, 200, 80, Color.blue, Color.WHITE);
		
		ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				Game g = MainMenu.this.state.g;
				
				if (solo.isHit(p)) {
					g.changeState(new PlayState(g));
				} else if (multi.isHit(p)) {
					g.changeState(new NetworkMenuState(g));
				}
			}
		};
		
		canvas.addMouseListener(ml);
	}
	
	public void draw(Graphics2D g2d) {		
		title.draw(g2d);
		solo.draw(g2d);
		multi.draw(g2d);
	}
	
	public void cleanup() {
		canvas.removeMouseListener(ml);
	}
}
