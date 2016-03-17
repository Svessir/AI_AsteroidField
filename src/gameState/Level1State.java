package gameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import entity.Rocket;
import entity.Target;
import robot.PlayerBot;
import tileMap.Background;

public class Level1State extends GameState{
	private Background bg;
	private Rocket rocket;
	private Target target;
	private final double SINGLE_ROTATION = Math.toRadians(2);
	
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		rocket = new Rocket(100, 150, 15, 30, 1, 0, gsm.boundaryRectangle);
		
		try{
			bg = new Background("/Background/space_background4.jpg", 1);
			bg.setVector(-0.1, 0);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void init() {
		Thread thread = new Thread(new PlayerBot());
		thread.start();
	}

	@Override
	public void update() {
		bg.update();
	}

	@Override
	public void draw(Graphics2D g) {
		// draw bg
		bg.draw(g);
		rocket.draw(g);
	}

	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_RIGHT)
			rocket.rotate(SINGLE_ROTATION);
		else if(k == KeyEvent.VK_LEFT)
			rocket.rotate(-SINGLE_ROTATION);
		else if(k == KeyEvent.VK_UP)
			rocket.move();
	}

	@Override
	public void keyReleased(int k) {
	}

}
