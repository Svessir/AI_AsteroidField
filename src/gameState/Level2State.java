package gameState;

import java.awt.Graphics2D;

import entity.Asteroid;
import entity.Rocket;
import entity.Target;

public class Level2State extends Level1State {
	
	private Asteroid[] asteroids;
	
	public Level2State(GameStateManager gsm, String levelName, Rocket r, Target t, Asteroid[] list) {
		super(gsm, levelName, r, t);
		asteroids = list;
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
	}

}
