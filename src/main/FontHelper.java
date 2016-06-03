package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontHelper {
	public static Font makeFont(String name, float size) {
		try {
			return Font.createFont(Font.PLAIN, new File("assets/fonts/" + name)).deriveFont(Font.PLAIN, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
