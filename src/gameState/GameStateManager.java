package gameState;

import java.awt.Rectangle;
import java.util.ArrayList;

import entity.Results;
import entity.Rocket;
import entity.Target;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	public Rectangle boundaryRectangle;
	
	public static final int MENUSTATE = 0;
	public static final int RESULTSTATE = 1;
	public static final int LEVEL1STATE = 2;
	
	public GameStateManager(Rectangle boundaryRectangle) {
		this.boundaryRectangle = boundaryRectangle;
		gameStates = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new ResultState(this));
		gameStates.add(new Level1State(this, new Rocket(100, 150, 1, 0, boundaryRectangle), new Target(50, 50)));
	}
	
	public void setState(int state) {
		if(state == RESULTSTATE) {
			Results r = gameStates.get(currentState).getResults();
			gameStates.get(currentState).init();
			currentState = state;
			gameStates.get(currentState).init(r);
			return;
		}
		currentState = state;
		gameStates.get(currentState).init();
	}
	
	public void update() {
		gameStates.get(currentState).update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}
	
	public boolean isGameOver() {
		return gameStates.get(currentState).isGameOver();
	}
}
