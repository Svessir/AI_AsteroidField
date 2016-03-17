package entity;

import java.awt.Rectangle;

public class MapObject {
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
	
	public Rectangle getRectangle() {
		return new Rectangle((int)x - (height/2), (int)y - (width/2), height, width);
	}
	
	//public void draw() {}
}
