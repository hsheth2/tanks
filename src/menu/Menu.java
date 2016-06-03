package menu;

import java.awt.Font;
import java.util.HashMap;

import main.FontHelper;
import map.Drawable;

public abstract class Menu implements Drawable {
	public static final HashMap<String, Font> fonts;
	
	static {
		fonts = new HashMap<String, Font>();
		
		fonts.put("Heading", FontHelper.makeFont("Bebas.ttf", 96f));
	}
}
