package controller;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class KeyboardController {
	private static final char[] keys = { 'w', 's', 'a', 'd' };

	private boolean[] pressed = { false, false, false, false };

	public KeyboardController(JPanel canvas) {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent ke) {
				synchronized (KeyboardController.class) {
					for (int i = 0; i < keys.length; i++) {
						if (ke.getKeyChar() == keys[i]) {
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
				return false;
			}
		});

		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				System.out.println("Mouse pressed; # of clicks: " + e.getClickCount());
				KeyboardController.this.mouseClicked();
			}

			// public void mouseReleased(MouseEvent e) {
			// System.out.println("Mouse released; # of clicks: " +
			// e.getClickCount());
			// }

			// public void mouseEntered(MouseEvent e) {
			// System.out.println("Mouse entered");
			// }

			// public void mouseExited(MouseEvent e) {
			// System.out.println("Mouse exited");
			// }

			// public void mouseClicked(MouseEvent e) {
			// System.out.println("Mouse clicked (# of clicks: " +
			// e.getClickCount() + ")");
			// }

			// public void mouseMoved(MouseEvent e) {
			// System.out.println("Mouse moved");
			// }
		});
	}
	
	private void mouseClicked() {
		// TODO this
		for (boolean x : pressed) {
			System.out.print(" " + x);
		}
		System.out.println();
	}
}
