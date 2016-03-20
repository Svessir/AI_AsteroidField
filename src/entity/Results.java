package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Results extends MapObject{
	
	private String shortestDistance;
	private String distanceTraveled;
	private String fuelSpent;
	private Color headerColor;
	private Font headerFont;
	private Font font;
	
	public Results(double x, double y, int width, int height, double shortestD, double travelD, double fuelSpent) {
		super(x, y, width, height);
		
		this.shortestDistance = Double.toString(shortestD);
		this.distanceTraveled = Double.toString(travelD);
		this.fuelSpent = Double.toString(fuelSpent);
	
		headerColor = new Color(128, 0, 0);
		headerFont = new Font("Century Gothic", Font.PLAIN, 16);
		font = new Font("Arial", Font.PLAIN, 10);
	}

	@Override
	public Rectangle getRectangle() {
		return new Rectangle((int)x - (width/2), (int)y - (height/2), width, height);
	}

	@Override
	public void draw(Graphics2D g) {
		Rectangle2D r = getRectangle();
		g.setColor(Color.GRAY);
		g.fill(r);
		
		g.setColor(headerColor);
		g.setFont(headerFont);
		g.drawString("Game Over!", 110, 90);
		
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString("Shortest Distance: " + shortestDistance, 95, 110);
		g.drawString("Travel Distance: " + distanceTraveled, 95, 120);
		g.drawString("Fuel Spent: " + fuelSpent, 95, 130);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(130, 140, 50, 15);
		
		g.setColor(Color.LIGHT_GRAY);
		g.drawString("Enter", 140, 150);
	}

}
