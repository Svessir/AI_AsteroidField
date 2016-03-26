package gameState;

import java.awt.Rectangle;
import java.util.ArrayList;

import entity.Results;
import entity.Rocket;
import entity.Target;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	private boolean isAI = false;
	public Rectangle boundaryRectangle;
	
	public static final int MENUSTATE = 0;
	public static final int RESULTSTATE = 1;
	public static final int LEVELSELECTIONSTATE = 2;
	public static final int LEVEL1STATE = 3;
	
	public GameStateManager(Rectangle boundaryRectangle) {
		this.boundaryRectangle = boundaryRectangle;
		gameStates = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new ResultState(this));
		gameStates.add(new LevelSelectionState(this));
		gameStates.add(new Level1State(this, "Level 1", new Rocket(20, 20, 1, 0, boundaryRectangle), new Target(100, 20)));
	}
	
	public void setState(int state) {
		if(state == RESULTSTATE) {
			gameStates.get(currentState).turnOffAi();
			Results r = gameStates.get(currentState).getResults();
			//gameStates.get(currentState).init();
			currentState = state;
			ResultState s = (ResultState)gameStates.get(currentState);
			s.init(r);
			return;
		}
		currentState = state;
		
		GameState gameState = gameStates.get(currentState);
		gameState.setAI(isAI);
		gameState.init();
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
	
	public ArrayList<String> getLevelNames() {
		ArrayList<String> names = new ArrayList<>();
		for(int i = 0; i < gameStates.size(); i++) {
			if(i != LEVEL1STATE && i != MENUSTATE)
				continue;
			GameState s = gameStates.get(i);
			names.add(s.getName());
		}
		return names;
	}
	
	public void setAi(boolean isAI) { this.isAI = isAI; }
}
