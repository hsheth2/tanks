package controller;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import main.Window;
import map.Map;
import map.Tank;
import physics.Vector;

public class KeyboardController extends Controller {
	private static final char[] keys = { 'w', 's', 'a', 'd' };

	private boolean[] pressed = { false, false, false, false };

	public KeyboardController(Map map, Tank tank, JPanel canvas) {
		super(map, tank);
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent ke) {
				synchronized (KeyboardController.class) {
					for (int i = 0; i < keys.length; i++) {
						if (ke.getKeyChar() == keys[i] || ke.getKeyChar() == Character.toUpperCase(keys[i])) {
							switch (ke.getID()) {
							case KeyEvent.KEY_PRESSED:
								pressed[i] = true;
								break;
							case KeyEvent.KEY_RELEASED:
								pressed[i] = false;
								break;
							}
						}
					}
				}
				updateDirection();
				return false;
			}
		});

		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
//				System.out.println("Mouse pressed; # of clicks: " + e.getClickCount());
				KeyboardController.this.mouseClicked(e.getPoint());
			}
		});
	}
	
	private void mouseClicked(Point p) {
//		System.out.println("Point:"+p.getX() + " " + p.getY());
		Vector clickPos = Window.real2game(p);
		this.shoot(clickPos);
	}
	
	private void updateDirection() {
		Vector dir = Vector.ZERO;
		if (pressed[0])
			dir = dir.add(Vector.UP);
		if (pressed[1])
			dir = dir.add(Vector.DOWN);
		if (pressed[2])
			dir = dir.add(Vector.LEFT);
		if (pressed[3])
			dir = dir.add(Vector.RIGHT);
		
		setDir(dir);
	}
}
