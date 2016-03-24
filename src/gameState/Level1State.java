package gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entity.Results;
import entity.Rocket;
import entity.Target;
import robot.GameInfo;
import robot.PlayerBot;
import tileMap.Background;

public class Level1State extends GameState{
	private Background bg;
	private Rocket rocket;
	private Target target;
	private double shortestDistance;
	private double rocketInitialX;
	private double rocketInitialY;
	private double targetInitialX;
	private double targetInitialY;
	
	
	public Level1State(GameStateManager gsm, String levelName ,Rocket r, Target t) {
		super(gsm, levelName);
		rocket = r;
		target = t;
		rocketInitialX = r.getX();
		rocketInitialY = r.getY();
		targetInitialX = t.getX();
		targetInitialY = t.getY();
		
		shortestDistance = rocket.calculateDistance(rocket.getX(), rocket.getY(), target.getX(), target.getY());
		
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
		rocket = new Rocket(rocketInitialX, rocketInitialY, 1, 0, gsm.boundaryRectangle);
		target = new Target(targetInitialX, targetInitialY);
		
		if(isAI) {
			GameInfo info = new GameInfo
			(
				rocket.getX(), 
				rocket.getDy(), 
				rocket.getDx(), 
				rocket.getDy(), 
				target.getX(), 
				target.getY(), 
				rocket.getFuelSpent()
			);
			
			Thread thread = new Thread(new PlayerBot(info));
			thread.start();
		}
	}

	@Override
	public void update() {
		bg.update();
	}

	@Override
	public void draw(Graphics2D g) {
		if(target == null || rocket == null)
			init();
		
		bg.draw(g);
		target.draw(g);
		rocket.draw(g);
	}

	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_RIGHT)
			rocket.rotateRight();
		else if(k == KeyEvent.VK_LEFT)
			rocket.rotateLeft();
		else if(k == KeyEvent.VK_UP)
			rocket.thrust();
		else {/* Move by Gravity */}
	}

	@Override
	public void keyReleased(int k) {
	}

	@Override
	public boolean isGameOver() {
		return rocket.collides(target);
	}

	@Override
	public Results getResults() { 
		return new Results(
				gsm.boundaryRectangle.getCenterX(), 
				gsm.boundaryRectangle.getCenterY(), 
				150, 
				100,
				shortestDistance,
				rocket.getDistanceTraveled()
				+ rocket.calculateDistance(rocket.getX(), rocket.getY(), target.getX(), target.getY()),
				rocket.getFuelSpent());
	}
}
