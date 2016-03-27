package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import utility.Helper;

public class Asteroid extends MapObject{
	
	private double weight;
	private double gravityFieldRadius;
	
	// Image
	private BufferedImage image;
	
	public Asteroid(double x, double y, double weight) {
		super(x, y, 30 * (int)weight, 30 * (int)weight);
		gravityFieldRadius = 100 * weight;
		
		BufferedImage original;
		try {
			original = ImageIO.read(getClass().getResourceAsStream("/Sprites/asteroid.png"));
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
	public Rectangle getRectangle() {
		return null;
	}

	@Override
	public void draw(Graphics2D g) {
		// draw gravity field
		g.setColor(new Color(153, 255, 255, 55));
		g.fillOval((int)x - (int)(gravityFieldRadius/2), (int)y - (int)(gravityFieldRadius/2), (int)gravityFieldRadius, (int)gravityFieldRadius);
		
		// draw asteroid
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.translate(-(height/2), -(width/2));
		g.drawImage(image, at, null);
	}
	
	public boolean isInRadius(MapObject o) {
		return Helper.calculateDistance(x, y, o.x, o.y) <= gravityFieldRadius;
	}
}
