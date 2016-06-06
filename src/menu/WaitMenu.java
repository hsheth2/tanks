package menu;

import java.awt.Graphics2D;

import javax.swing.JPanel;

import main.WaitState;

public class WaitMenu extends Menu {
	public WaitMenu(WaitState state, JPanel canvas) {
		super((Graphics2D) canvas.getGraphics());
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}
}
