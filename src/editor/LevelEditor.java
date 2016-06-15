package editor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.Config;

public class LevelEditor extends JFrame {
	public Grid g;

	public JTextField name;
	public JButton save, open, clear;
	public JLabel mode;

	public LevelEditor() {
		g = new Grid(Config.GRID_WIDTH, Config.GRID_HEIGHT, this);

		g.setPreferredSize(new Dimension(Config.WIDTH * Tile.SIZE, Config.HEIGHT * Tile.SIZE));

		getContentPane().add(g);
		getContentPane().setLayout(null);

		g.setBounds(200, 80, 400, 300);

		name = new JTextField("");
		name.setBounds(200, 400, 400, 40);
		add(name);

		save = new JButton("Save");
		save.setBounds(240, 480, 80, 40);
		add(save);

		open = new JButton("Open");
		open.setBounds(480, 480, 80, 40);
		add(open);

		clear = new JButton("Clear");
		clear.setBounds(360, 480, 80, 40);
		add(clear);

		mode = new JLabel("Mode: Ground");
		mode.setBounds(360, 20, 80, 40);
		add(mode);

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Level l = g.toLevel();
				String path = name.getText();

				try {
					l.save(path);
					JOptionPane.showMessageDialog(null, "Level saved to assets/levels/" + path + ".");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String path = name.getText();

				try {
					g.load(Level.load(path));
					JOptionPane.showMessageDialog(null, "Level loaded from assets/levels/" + path + ".");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Cannot load assets/levels/" + path + ".");
				}

				g.repaint();
			}
		});

		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				g.init();
				g.repaint();
			}
		});

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setTitle("Level Editor");
		setVisible(true);
		setResizable(false);
		setSize(800, 600);
	}

	public static void main(String[] args) {
		new LevelEditor();
	}
}
