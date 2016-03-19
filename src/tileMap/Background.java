package tileMap;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

import main.GamePanel;

public class Background {
	
	private BufferedImage image;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale;
	
	public Background(String s, double ms)  {
		
		try{
			BufferedImage original = ImageIO.read(getClass().getResourceAsStream(s));
			image = new BufferedImage(320, 240, original.getType());
			Graphics2D g = image.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(original, 0, 0, 320, 240, 0, 0, original.getWidth(),original.getHeight(), null);
			g.dispose();
			moveScale = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		//x = dx;
		//y = dy;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
		
		if(x < 0) {
			g.drawImage(image, (int)x + GamePanel.WIDTH, (int)y, null);
		}
		if(x > 0) {
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
		}
	}
}
