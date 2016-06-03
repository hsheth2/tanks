package main;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.File;

public class FontHelper {
	public static Font makeFont(String name, float size) {
		try {
			return Font.createFont(Font.PLAIN, new File("assets/fonts/" + name)).deriveFont(Font.PLAIN, size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static int stringWidth(String s, Font f, Graphics2D g2d) {
		FontMetrics fm = g2d.getFontMetrics(f);
		
		return fm.stringWidth(s);
	}
	
	public static int centerStringX(String s, int w, Font f, Graphics2D g2d) {
		FontMetrics fm = g2d.getFontMetrics(f);
		
		return (w - fm.stringWidth(s)) / 2;
	}
	
	public static int centerStringY(int h, Font f, Graphics2D g2d) {
		FontMetrics fm = g2d.getFontMetrics(f);
		
		return (h - fm.getHeight()) / 2 + fm.getAscent();
	}
}
