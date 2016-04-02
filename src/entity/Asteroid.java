package entity;

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
	
	// Image
	private BufferedImage image;
	
	// collision radius
	private double collistionRadius;
	
	public Asteroid(double x, double y, double weight) {
		super(x, y, 30 * (int)weight, 30 * (int)weight);
		this.weight = weight;
		
		collistionRadius = height/2.0 - height * 0.06;
		
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
		return new Rectangle((int)x - (width/2), (int)y - (height/2), width, height);
	}
	
	@Override
	public boolean collides(MapObject o) {
		// Only the rocket will be able to collide with asteroids
		if(!(o instanceof Rocket)) return false;
		
		Rocket rocket = (Rocket) o;
		double rocketPadding = rocket.getHeight() / 2.0;
		return Helper.calculateDistance(rocket.getX(), rocket.getY(), x, y) < collistionRadius + rocketPadding;
	}

	@Override
	public void draw(Graphics2D g) {
		// draw asteroid
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.translate(-(height/2), -(width/2));
		g.drawImage(image, at, null);
	}
	
	public Asteroid copy() {
		return new Asteroid(x, y, weight);
	}
}
