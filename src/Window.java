import java.awt.*;
import javax.swing.*;

public class Window extends JFrame {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	private Canvas canvas;
	
	public Window() {
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));

		getContentPane().add(canvas);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setTitle("THIS IS MY GAME");
		setVisible(true);
	}
	
	private class Canvas extends JPanel {
		public void paintComponent(Graphics g) {
			
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Window();
			}
		});
	}
}
