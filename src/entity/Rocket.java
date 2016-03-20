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
	
	public Rocket(double x, double y, int width, int height, double dx, double dy, Rectangle boundary) {
		super(x, y, width, height);
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
	
	public void rotate(double angle) {
		double oldDx = dx;
		dx = dx * Math.cos(angle) - dy * Math.sin(angle);
		dy = oldDx * Math.sin(angle) + dy * Math.cos(angle);
	}
	
	public void thrust() {
		double newX = x + dx;
		double newY = y + dy;
		if(boundary.contains(newX, newY)) {
			x = newX;
			y = newY;
		}
	}
	/*
	public static void main(String[] args) {
		double angle1 = 90;
		angle1 = Math.toRadians(angle1);
		double dx = 0, dy = 1;
		double oldDx = dx;
		dx = (dx * Math.cos(angle1) - dy * Math.sin(angle1));
		dy = (oldDx * Math.sin(angle1) + dy * Math.cos(angle1));
		double angle2 = Math.atan2(dy, dx);
		System.out.println(angle2);
	}*/
}
