package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class MapObject {
	// position
	protected double x;
	protected double y;
	
	// dimension
	protected int width;
	protected int height;
	
	public MapObject(double x, double y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract Rectangle getRectangle();
	
	public abstract void draw(Graphics2D g);
	
	public boolean collides(MapObject o) {
		return o.getRectangle().contains(x, y);
	}
	
	public int getWidth() { return width; }
	
	public int getHeight() { return height; }
	
	public double getX() { return x; }
	
	public double getY() { return y; }
}
