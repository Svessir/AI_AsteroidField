package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import utility.Helper;

public class Target extends MapObject{
	
	// image
	BufferedImage image;
	
	public Target(double x, double y) {
		super(x, y, 30, 30);
		
		BufferedImage original;
		try {
			original = ImageIO.read(getClass().getResourceAsStream("/Sprites/astronaut.png"));
			image = new BufferedImage(width, height, original.getType());
			Graphics2D g = image.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(original, 0, 0, width, height, 0, 0, original.getWidth(),original.getHeight(), null);
			g.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.translate(-(height/2), -(width/2));
		g.drawImage(image, at, null);
	}

	@Override
	public Rectangle getRectangle() {
		return new Rectangle((int)x - (width/2), (int)y - (height/2), width, height);
	}

	@Override
	public boolean collides(MapObject o) {
		return Helper.calculateDistance(x, y, o.getX(), o.getY()) <= width;
	}

}
