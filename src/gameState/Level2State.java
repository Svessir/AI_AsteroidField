package gameState;

import java.awt.Graphics2D;

import entity.Asteroid;
import entity.Results;
import entity.Rocket;
import entity.Target;
import robot.GameInfo;
import robot.PlayerBot;

public class Level2State extends Level1State {
	
	private Asteroid[] asteroids;
	
	public Level2State(GameStateManager gsm, String levelName, Rocket r, Target t, Asteroid[] list) {
		super(gsm, levelName, r, t);
		asteroids = list;
	}
	
	@Override
	public void init() {
		rocket = new Rocket(rocketInitialX, rocketInitialY, 1, 0, gsm.boundaryRectangle);
		target = new Target(targetInitialX, targetInitialY);
		
		if(isAI) {
			GameInfo info = new GameInfo
			(
				rocket.getX(), 
				rocket.getY(), 
				rocket.getDx(), 
				rocket.getDy(), 
				rocket.getFuelSpent(),
				rocket.getSingleRotation(),
				rocket.getFuelConsumptionForSingleThrust(),
				gsm.boundaryRectangle,
				new Target(targetInitialX, targetInitialY),
				copyAsteroids()
			);
			
		    aiThread = new PlayerBot(info);
			aiThread.start();
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(target == null || rocket == null)
			init();
		
		bg.draw(g);
		
		for(Asteroid asteroid : asteroids)
			asteroid.draw(g);
		
		target.draw(g);
		rocket.draw(g);
		//rocket.print();
	}
	
	@Override
	public boolean isGameOver() {
		 boolean isGameOver = super.isGameOver();
		 
		 if(!isGameOver) {
			 for(Asteroid asteroid : asteroids) {
				 if(asteroid.collides(rocket))
					 return true;
			 }
		 }
		 
		 return isGameOver;
	}
	
	private Asteroid[] copyAsteroids() {
		Asteroid[] newAsteroids = new Asteroid[asteroids.length];
		for(int i = 0; i < asteroids.length; i++) {
			newAsteroids[i] = asteroids[i].copy();
		}
		return newAsteroids;
	}
	
	@Override
	public Results getResults() {
		if(super.isGameOver())
			return super.getResults();
		
		return new Results(
				gsm.boundaryRectangle.getCenterX(), 
				gsm.boundaryRectangle.getCenterY(), 
				150, 
				100,
				shortestDistance,
				rocket.getDistanceTraveled(),
				rocket.getFuelSpent());
	}
}
