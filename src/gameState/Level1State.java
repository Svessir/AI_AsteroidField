package gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entity.Results;
import entity.Rocket;
import entity.Target;
import robot.PlayerBot;
import tileMap.Background;

public class Level1State extends GameState{
	private Background bg;
	private Rocket rocket;
	private Target target;
	private double shortestDistance;
	private final double SINGLE_ROTATION = Math.toRadians(2);
	private double rocketInitialX;
	private double rocketInitialY;
	private double targetInitialX;
	private double targetInitialY;
	
	
	public Level1State(GameStateManager gsm, Rocket r, Target t) {
		super(gsm);
		rocket = r;
		target = t;
		rocketInitialX = r.getX();
		rocketInitialY = r.getY();
		targetInitialX = t.getX();
		targetInitialY = t.getY();
		
		shortestDistance = calculateDistance();
		
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
		//Thread thread = new Thread(new PlayerBot());
		//thread.start();
	}
	
	@Override
	public void init(Results r) {}

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
			rocket.rotate(SINGLE_ROTATION);
		else if(k == KeyEvent.VK_LEFT)
			rocket.rotate(-SINGLE_ROTATION);
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
				0,
				0);
	}
	
	private double calculateDistance() {
		double xDist = Math.max(rocket.getX(), target.getX()) - Math.min(rocket.getX(), target.getX());
		double yDist = Math.max(rocket.getY(), target.getY()) - Math.min(rocket.getY(), target.getY());
		return Math.round(Math.sqrt(xDist * xDist + yDist * yDist));
	}
}
