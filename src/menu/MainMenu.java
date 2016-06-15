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
import main.Palette;
import states.GameState;
import states.HelpState;
import states.NetworkMenuState;

public class MainMenu extends Menu {
	private MouseListener ml;

	private Heading title;
	private Button play, help;

	public MainMenu(GameState state, JPanel canvas) {
		super(state, canvas);

		canvas.setBackground(Color.WHITE);

		g2d.setFont(Heading.FONT);
		title = new Heading("TANKS", FontHelper.centerStringX("TANKS", Config.WIDTH, g2d), 200, Palette.BLACK);
		g2d.setFont(Button.FONT);
		play = new Button("Play", MenuItem.centerX(200), 250, 200, 80, Palette.GREEN, Color.WHITE);
		help = new Button("Help", MenuItem.centerX(200), 350, 200, 80, Palette.DARK_GRAY, Color.WHITE);

		ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				Game g = MainMenu.this.state.g;

				if (play.isHit(p)) {
					g.changeState(new NetworkMenuState(g));
				} else if (help.isHit(p)) {
					g.changeState(new HelpState(g));
				}
			}
		};

		canvas.addMouseListener(ml);
	}

	public void draw(Graphics2D g2d) {
		title.draw(g2d);
		play.draw(g2d);
		help.draw(g2d);
	}

	public void cleanup() {
		canvas.removeMouseListener(ml);
	}
}
