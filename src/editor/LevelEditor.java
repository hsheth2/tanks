package editor;

import java.awt.Dimension;

import javax.swing.JFrame;

import main.Config;

public class LevelEditor extends JFrame {
	public Grid g;
	
	public LevelEditor() {
		g = new Grid(Config.GRID_WIDTH, Config.GRID_HEIGHT);
		
		g.setPreferredSize(new Dimension(Config.WIDTH*Tile.SIZE, Config.HEIGHT*Tile.SIZE));

		getContentPane().add(g);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setTitle("Level Editor");
		setVisible(true);
		setResizable(true);
		setSize(1000, 600);
	}
	
	public static void main(String[] args) {
		new LevelEditor();
	}
}
