package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rocket extends MapObject{
	
	// boundary rectangle defines the area where the rocket can travel
	private Rectangle boundary;
	
	// direction vector
	private double dx;
	private double dy;
	
	// image
	BufferedImage image;
	
	// fuel
	double fuel_consumption = 0;
	
	// travel distance
	double distanceTraveled = 0;
	
	// single rotation
	private final double SINGLE_ROTATION = Math.toRadians(1);
	
	public Rocket(double x, double y, double dx, double dy, Rectangle boundary) {
		super(x, y, 30, 30);
		this.boundary = boundary;
		this.dx = dx;
		this.dy = dy;
		
		BufferedImage original;
		try {
			original = ImageIO.read(getClass().getResourceAsStream("/Sprites/rocket_cropped.png"));
			image = new BufferedImage(height, width, original.getType());
			Graphics2D g = image.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(original, 0, 0, height, width, 0, 0, original.getWidth(),original.getHeight(), null);
			g.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		/*Rectangle2D r = getRectangle();
		double angle = Math.atan2(dy, dx);
		AffineTransform at = AffineTransform.getRotateInstance(angle, x, y);
		g.setColor(Color.RED);
		//g.fill(r);
		g.fill(at.createTransformedShape(r));
		g.setColor(Color.BLUE);
		g.fillOval((int)x, (int)y, 5, 5);*/
		double angle = Math.atan2(dy, dx);
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.rotate(angle);
		at.translate(-(height/2), -(width/2));
		g.drawImage(image, at, null);
	}
	
	public void rotateLeft() {
		rotate(-SINGLE_ROTATION);
	}
	
	public void rotateRight() {
		rotate(SINGLE_ROTATION);
	}
	
	private void rotate(double angle) {
		double oldDx = dx;
		dx = dx * Math.cos(angle) - dy * Math.sin(angle);
		dy = oldDx * Math.sin(angle) + dy * Math.cos(angle);
	}
	
	public void thrust() {
		double newX = x + dx;
		double newY = y + dy;
		if(boundary.contains(newX, newY)) {
			fuel_consumption += 0.25;
			distanceTraveled += calculateDistance(x, y, newX, newY);
			x = newX;
			y = newY;
		}
	}
	
	public double getDx() { return dx; }
	
	public double getDy() { return dy; }

	@Override
	public Rectangle getRectangle()  {
		return new Rectangle((int)x - (height/2), (int)y - (width/2), height, width);
	}
	
	public double getFuelSpent() {
		return fuel_consumption;
	}
	
	public double getDistanceTraveled() {
		return distanceTraveled;
	}
	
	public double calculateDistance(double oldX, double oldY, double newX, double newY) {
		double xDist = Math.max(oldX, newX) - Math.min(oldX, newX);
		double yDist = Math.max(oldY, newY) - Math.min(oldY, newY);
		return Math.round(Math.sqrt(xDist * xDist + yDist * yDist));
	}
}
