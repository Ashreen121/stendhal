package games.stendhal.client.gui.styled;

import games.stendhal.client.sprite.Sprite;

import java.awt.Graphics;

import javax.swing.UIManager;

public class StyleUtil {
	/**
	 * Get the current <code>Style</code>, or <code>null</code> if it
	 * has not been set in the UIManager.
	 */
	public static Style getStyle() {
		Object obj = UIManager.get("StendhalStyle");
		if (obj instanceof Style) {
			return (Style) obj;
		}
		
		return null;
	}
	
	/**
	 * Fill an area with the background sprite of a {@link Style}.
	 * 
	 * @param style the style to be used
	 * @param graphics
	 * @param x left x coordinate
	 * @param y top y coordinate
	 * @param width width of the area
	 * @param height height of the area
	 */
	public static void fillBackground(Style style, Graphics graphics, int x, 
			int y, int width, int height) {
		// Prepare clipping
		graphics = graphics.create();
		graphics.clipRect(x, y, width, height);
		
		Sprite image = style.getBackground();
		
		for (int i = x; i < x + width; i += image.getWidth()) {
			for (int j = y; j < y + height; j += image.getHeight()) {
				image.draw(graphics, i, j);
			}
		}
		graphics.dispose();
	}
	
	/**
	 * Paint disabled text using a style's highlight and shadow colors.
	 *  
	 * @param style style to be used
	 * @param g graphics
	 * @param text painted string
	 * @param x left x coordinate
	 * @param y baseline y coordinate
	 */
	void paintDisabledText(Style style, Graphics g, String text, int x, int y) {
		g.setColor(style.getHighLightColor());
		g.drawString(text, x + 1, y + 1);
		g.setColor(style.getShadowColor());
		g.drawString(text, x, y);
	}
}
