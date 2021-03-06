package editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Level implements Serializable {
	public char[][] grid;

	public Level(char[][] grid) {
		this.grid = grid;
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

	public static Level load(String name) throws IOException {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("assets/levels/" + name)));

			Level loaded = (Level) ois.readObject();
			ois.close();

			return loaded;
		} catch (ClassNotFoundException | NullPointerException e) {
			// eat it
			return null;
		} catch (IOException e) {
			throw e;
		}
	}
}
