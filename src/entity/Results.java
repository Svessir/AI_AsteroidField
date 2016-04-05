package entity;

import main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Results extends MapObject{
	
	private String shortestDistance;
	private String distanceTraveled;
	private String fuelSpent;
	private Color failureHeaderColor;
	private Color successHeaderColor;
	private Font headerFont;
	private Font font;
	private boolean isSuccess;
	private double time;
	
	public Results( double x, 
					double y,
					int width,
					int height,
					double shortestD,
					double travelD,
					double fuelSpent,
					boolean isSuccess,
					double time) {
		super(x, y, width, height);
		
		this.shortestDistance = Double.toString(shortestD);
		this.distanceTraveled = Double.toString(travelD);
		this.fuelSpent = Double.toString(fuelSpent);
		this.isSuccess = isSuccess;
		this.time = time;
		
		failureHeaderColor = new Color(128, 0, 0);
		successHeaderColor = new Color(0, 128, 0);
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
		
		g.setFont(headerFont);
		
		String message = isSuccess ? "Success!" : "Failure!";
		
		Color headerColor = isSuccess ? successHeaderColor : failureHeaderColor;
		g.setColor(headerColor);

		///Fine tuning of text variable
		int width = GamePanel.WIDTH/2 - 50;

		g.drawString(message, width + 20, 200);

		g.setColor(Color.BLACK);
		g.setFont(font);

		g.drawString("Shortest Distance: " + shortestDistance, width, GamePanel.HEIGHT/2);
		g.drawString("Travel Distance: " + distanceTraveled, width, GamePanel.HEIGHT/2 + 10);
		g.drawString("Fuel Spent: " + fuelSpent, width, GamePanel.HEIGHT/2 + 20);
		g.drawString("Time: " + time, width, GamePanel.HEIGHT/2 + 30);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(width + 25, GamePanel.HEIGHT/2 + 60, 70, 20);

		g.setColor(Color.LIGHT_GRAY);
		g.drawString("Enter", width + 35, GamePanel.HEIGHT/2 + 70);
	}

}
