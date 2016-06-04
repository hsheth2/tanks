package editor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Level implements Serializable {
	public char[][] grid;
	
	public Level(int w, int h) {
		grid = new char[w][h];
	}
	
	public void save(String name) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("assets/levels/" + name)));
			
			oos.writeObject(this);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
