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
import states.GameState;
import states.MainMenuState;

public class HelpMenu extends Menu {
	private MouseListener ml;
	
	public Label title;
	public Button back, next, prev;
	public int page;
	public Page[] pages;
	public static final int NUM_PAGES = 5;
	
	public HelpMenu(GameState state, JPanel canvas) {
		super(state, canvas);
		
		pages = new Page[NUM_PAGES];
		
		pages[0] = new Page("Controls",
				new String[] {
						"WASD - Move",
						"Click - Shoot",
						"Space - Lay mine"
				}, 0, 300, this);
		pages[1] = new Page("Objective",
				new String[] {
						"It's every player for themself!",
						"Destroy your opponents without",
						"getting destroyed yourself."
				}, 0, 300, this);
		pages[2] = new Page("Bullets",
				new String[] {
						"Bullets can bounce off walls",
						"once before exploding. Use this",
						"to your advantage!"
				}, 0, 300, this);
		pages[3] = new Page("Walls",
				new String[] {
						"You can't shoot through or move",
						"through a wall."
				}, 0, 300, this);
		pages[4] = new Page("Holes",
				new String[] {
						"You can shoot through a hole, but",
						"you can't move over it."
				}, 0, 300, this);
		
		g2d.setFont(Label.FONT);
		title = new Label(pages[0].title, FontHelper.centerStringX(pages[0].title, Config.WIDTH, g2d), 200, Color.BLACK);
		
		g2d.setFont(Button.FONT);
		back = new Button("Back", 20, 20, 200, 80, Color.LIGHT_GRAY, Color.WHITE);
		next = new Button("Next", 440, 480, 200, 80, Color.LIGHT_GRAY, Color.WHITE);
		prev = new Button("Prev", 160, 480, 200, 80, Color.LIGHT_GRAY, Color.WHITE);
		
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
					title = new Label(pages[page].title, FontHelper.centerStringX(pages[page].title, Config.WIDTH, g2d), 200, Color.BLACK);
					
					if (page == NUM_PAGES - 1) {
						next.active = false;
					}
					
					prev.active = true;
				} else if (prev.isHit(p) && prev.active) {
					page--;
					
					g2d.setFont(Label.FONT);
					title = new Label(pages[page].title, FontHelper.centerStringX(pages[page].title, Config.WIDTH, g2d), 200, Color.BLACK);
					
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
