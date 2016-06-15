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
import states.MainMenuState;

public class HelpMenu extends Menu {
	private MouseListener ml;

	public Label title;
	public Button back, next, prev;
	public int page;
	public Page[] pages;

	public HelpMenu(GameState state, JPanel canvas) {
		super(state, canvas);

		pages = new Page[] { new Page("Controls", new String[] { "WASD - Move", "Click - Shoot", "Space - Lay mine" }, 0, 300, this),
				new Page("Objective", new String[] { "It's every player for themself!", "Destroy your opponents without", "getting destroyed yourself." }, 0, 300, this),
				new Page("Bullets", new String[] { "Bullets can bounce off walls", "once before exploding. Use this", "to your advantage!" }, 0, 300, this),
				new Page("Walls", new String[] { "Walls are gray. You can't shoot through", "or move through a wall." }, 0, 300, this), new Page("Holes", new String[] { "Holes are black. You can shoot through", "a hole, but you can't move over it." }, 0, 300, this),
				new Page("Mines", new String[] { "Mines are purple. Mines explode after 5 seconds", "or after being shot, whichever comes first." }, 0, 300, this),
				new Page("Grace Period", new String[] { "In the event that players spawn near each other,", "a 3-second grace period is provided at the start", "of each game.  Nobody can shoot or", "lay mines during this period." }, 0, 300, this),
				new Page("Server (1/2)", new String[] { "You need a server to play Tanks. Just run", " the server and click on 'Start a New Game'.", "Up to 4 players can play." }, 0, 300, this),
				new Page("Server (2/2)", new String[] { "Everyone needs to enter the IP address", "displayed on the server. Once everyone is", "connected, click 'Start Game' on the server." }, 0, 300, this),
				new Page("Level Editor (1/2)", new String[] { "You can design your own levels using", "the Level Editor. You can easily load and save files", "using the provided buttons." }, 0, 300, this),
				new Page("Level Editor (2/2)", new String[] { "Press 'G' to create empty ground.", "Press 'W' to create walls.", "Press 'H' to create holes. ", "Clicking on the map will create the specified object." }, 0, 300, this), };

		g2d.setFont(Label.FONT);
		title = new Label(pages[0].title, FontHelper.centerStringX(pages[0].title, Config.WIDTH, g2d), 200, Palette.REALLY_BLACK);

		g2d.setFont(Button.FONT);
		back = new Button("Back", MenuItem.centerX(200), 40, 200, 80, Palette.DARK_GRAY, Color.WHITE);
		next = new Button("Next", 440, 480, 200, 80, Palette.DARK_GRAY, Color.WHITE);
		prev = new Button("Prev", 160, 480, 200, 80, Palette.DARK_GRAY, Color.WHITE);

		prev.active = false;

		g2d.setFont(Text.FONT);
		page = 0;

		ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				Game g = HelpMenu.this.state.g;

				if (back.isHit(p)) {
					g.changeState(new MainMenuState(g));
				} else if (next.isHit(p) && next.active) {
					page++;

					g2d.setFont(Label.FONT);
					title = new Label(pages[page].title, FontHelper.centerStringX(pages[page].title, Config.WIDTH, g2d), 200, Palette.REALLY_BLACK);

					if (page == pages.length - 1) {
						next.active = false;
					}

					prev.active = true;
				} else if (prev.isHit(p) && prev.active) {
					page--;

					g2d.setFont(Label.FONT);
					title = new Label(pages[page].title, FontHelper.centerStringX(pages[page].title, Config.WIDTH, g2d), 200, Palette.REALLY_BLACK);

					if (page == 0) {
						prev.active = false;
					}

					next.active = true;
				}
			}
		};

		canvas.addMouseListener(ml);
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (next.active) {
			next.draw(g2d);
		}

		if (prev.active) {
			prev.draw(g2d);
		}

		title.draw(g2d);
		back.draw(g2d);
		pages[page].draw(g2d);
	}

	@Override
	public void cleanup() {
		canvas.removeMouseListener(ml);
	}
}
