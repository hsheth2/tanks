package menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import controller.NetworkManager;
import main.Config;
import main.FontHelper;
import main.Game;
import main.Palette;
import states.GameState;
import states.MainMenuState;

public class EndMenu extends Menu {
	private MouseListener ml;
	private Label msg;
	private Button back;

	public EndMenu(GameState state, JPanel canvas, NetworkManager nm) {
		super(state, canvas);

		String message = "Game over. ";
		if (nm.peerCount > 1) {
			if (!nm.won) {
				message += "You lost!";
			} else {
				message += "You won!";
			}
		}

		g2d.setFont(Label.FONT);
		msg = new Label(message, FontHelper.centerStringX(message, Config.WIDTH, g2d), 200, Palette.BLACK);

		g2d.setFont(Button.FONT);
		back = new Button("Back", MenuItem.centerX(200), 400, 200, 80, Palette.DARK_GRAY, Color.WHITE);

		ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				Game g = EndMenu.this.state.g;

				if (back.isHit(p)) {
					g.changeState(new MainMenuState(g));
				}
			}
		};

		canvas.addMouseListener(ml);
	}

	@Override
	public void draw(Graphics2D g2d) {
		msg.draw(g2d);
		back.draw(g2d);
	}

	@Override
	public void cleanup() {
		canvas.removeMouseListener(ml);
	}
}
