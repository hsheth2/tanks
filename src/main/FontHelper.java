package main;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
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

	public static int stringWidth(String s, Graphics2D g2d) {
		FontMetrics fm = g2d.getFontMetrics(g2d.getFont());

		return fm.stringWidth(s);
	}

	public static int centerStringX(String s, int w, Graphics2D g2d) {
		FontMetrics fm = g2d.getFontMetrics(g2d.getFont());

		return (w - fm.stringWidth(s)) / 2;
	}

	public static int centerStringY(String s, int h, Graphics2D g2d) {
		FontMetrics fm = g2d.getFontMetrics(g2d.getFont());
		Rectangle r = fm.getStringBounds(s, g2d).getBounds();

		FontRenderContext frc = g2d.getFontRenderContext();
		GlyphVector gv = g2d.getFont().createGlyphVector(frc, s);
		Rectangle vb = gv.getVisualBounds().getBounds();

		return h / 2 - vb.height / 2 - vb.y;
	}
}
