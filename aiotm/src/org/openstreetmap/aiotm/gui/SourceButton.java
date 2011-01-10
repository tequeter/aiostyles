package org.openstreetmap.aiotm.gui;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.ImageIcon;

public class SourceButton {

	private int x = 0;
	private final int y = 30;

	private final ImageIcon enlargeImage;
	private final ImageIcon shrinkImage;
	private final ImageIcon imageMapnik;
	private final ImageIcon imageOsmarender;
	private final ImageIcon imageCycleMap;

	private boolean isEnlarged = false;

	private int currentMap = MAPNIK;

	public static final int HIDE_OR_SHOW = 1;
	public static final int MAPNIK = 2;
	public static final int OSMARENDER = 3;
	public static final int CYCLEMAP = 4;

	public SourceButton() {
		enlargeImage = new ImageIcon(ClassLoader.getSystemResource( "images/layer-switcher-maximize.png" ));
		shrinkImage = new ImageIcon(ClassLoader.getSystemResource( "images/layer-switcher-minimize.png"));
		imageMapnik = new ImageIcon(ClassLoader.getSystemResource( "images/blue_Mapnik.png"));
		imageOsmarender = new ImageIcon(ClassLoader.getSystemResource( "images/blue_Osmarender.png"));
		imageCycleMap = new ImageIcon(ClassLoader.getSystemResource( "images/blue_CycleMap.png"));
	}

	public void paint(Graphics g) {

		if (isEnlarged) {
			if (currentMap == MAPNIK) {
				g.drawImage(imageMapnik.getImage(), g.getClipBounds().width
						- imageMapnik.getIconWidth(), y, null);
			}else if(currentMap == CYCLEMAP){
				g.drawImage(imageCycleMap.getImage(), g.getClipBounds().width
						- imageCycleMap.getIconWidth(), y, null);
			}
			else {
				g.drawImage(imageOsmarender.getImage(), g.getClipBounds().width
						- imageMapnik.getIconWidth(), y, null);
			}

			if (shrinkImage != null) {
				this.x = g.getClipBounds().width - shrinkImage.getIconWidth();
				g.drawImage(shrinkImage.getImage(), x, y, null);
			}

		} else {
			if (enlargeImage != null) {
				this.x = g.getClipBounds().width - enlargeImage.getIconWidth();
				g.drawImage(enlargeImage.getImage(), x, y, null);
			}
		}
	}

	public void toggle() {
		this.isEnlarged = !this.isEnlarged;

	}

	public int hit(Point point) {
		if (isEnlarged) {
			if (x < point.x && point.x < x + shrinkImage.getIconWidth()) {
				if (y < point.y && point.y < y + shrinkImage.getIconHeight()) {
					return HIDE_OR_SHOW;
				}
			} else if (x - imageMapnik.getIconWidth() < point.x && point.x < x) {
				if (y < point.y && point.y < y + imageMapnik.getIconHeight() / 3) {
					currentMap = OSMARENDER;
					return OSMARENDER;
				} else if (y + imageMapnik.getIconHeight() / 3 < point.y
						&& point.y < y + imageMapnik.getIconHeight() *2/3) {
					currentMap = MAPNIK;
					return MAPNIK;
				} else if (y + imageMapnik.getIconHeight()* 2/3 < point.y
						&& point.y < y + imageMapnik.getIconHeight()) {
					currentMap = CYCLEMAP;
					return CYCLEMAP;
				}
			}
		} else {
			if (x < point.x && point.x < x + enlargeImage.getIconWidth()) {
				if (y < point.y && point.y < y + enlargeImage.getIconHeight()) {
					return HIDE_OR_SHOW;
				}
			}
		}

		return 0;
	}

	/**
	 * One of the constants OSMARENDER,MAPNIK or CYCLEMAP
	 */
	public void setMapStyle (int style) {
		currentMap = (style < 2 || style > 4) ? MAPNIK : style;
	}
}
