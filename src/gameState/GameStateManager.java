package gameState;

import java.awt.Rectangle;
import java.util.ArrayList;

import entity.Asteroid;
import entity.Results;
import entity.Rocket;
import entity.Target;
import robot.GameInfo;

public class GameStateManager {
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	private boolean isAI = false;
	public Rectangle boundaryRectangle;
	
	public static final int MENUSTATE = 0;
	public static final int RESULTSTATE = 1;
	public static final int LEVELSELECTIONSTATE = 2;
	public static final int LEVEL1STATE = 3;
	public static final int LEVEL2STATE = 4;
	public static final int LEVEL3STATE = 5;
	public static final int LEVEL4STATE = 6;
	public static final int LEVEL5STATE = 7;
	public static final int LEVEL6STATE = 8;
	public static final int LEVEL7STATE = 9;
	public static final int LEVEL8STATE = 10;
	
	public GameStateManager(Rectangle boundaryRectangle) {
		this.boundaryRectangle = boundaryRectangle;
		gameStates = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new ResultState(this));
		gameStates.add(new LevelSelectionState(this));
		gameStates.add(new Level1State(this, "Level 1", new Rocket(200, 200, 1, 0, boundaryRectangle), new Target(20, 20)));
		gameStates.add(new Level1State(this, "Level 2", new Rocket(20, 20, 1, 0, boundaryRectangle), new Target(200, 200)));
		gameStates.add(new Level1State(this, "Level 3", new Rocket(20, 220, 1, 0, boundaryRectangle), new Target(300, 20)));
		gameStates.add(new Level1State(this, "Level 4", new Rocket(300, 20, 1, 0, boundaryRectangle), new Target(20, 220)));
		gameStates.add(new Level2State(this, "Level 5", new Rocket(20, 20, 1, 0, boundaryRectangle), new Target(200, 200), getLevel5Asteroids()));
		gameStates.add(new Level2State(this, "Level 6", new Rocket(20, 220, 1, 0, boundaryRectangle), new Target(300, 20), getLevel6Asteroids()));
		gameStates.add(new Level2State(this, "Level 7", new Rocket(20, 220, 1, 0, boundaryRectangle), new Target(300,20), getLevel7Asteroids()));
		gameStates.add(new Level2State(this, "Level 8", new Rocket(20, 220, 1, 0, boundaryRectangle), new Target(230,20), getLevel8Asteroids()));
	}
	
	public void setState(int state) {
		if(state == RESULTSTATE) {
			gameStates.get(currentState).turnOffAi();
			Results r = gameStates.get(currentState).getResults();
			gameStates.get(currentState).init();
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
			if(!isListableStateIndex(i))
				continue;
			GameState s = gameStates.get(i);
			names.add(s.getName());
		}
		return names;
	}
	
	private boolean isListableStateIndex(int i) {
		return i != RESULTSTATE && i != LEVELSELECTIONSTATE;
	}
	
	public void setAi(boolean isAI) { this.isAI = isAI; }
	
	public GameInfo getRocketInfo() {
		GameState state = gameStates.get(currentState);
		
		if(!(state instanceof Level1State)) return null;
		
		Level1State lvlState = (Level1State)state;
		
		return lvlState.getRocketInfo();
	}
	
	private Asteroid[] getLevel5Asteroids() {
		Asteroid[] asteroids = {
			new Asteroid(100, 100, 3),
		};
		return asteroids;
	}
	
	private Asteroid[] getLevel6Asteroids() {
		Asteroid[] asteroids = {
			new Asteroid(200, 30, 1),
			new Asteroid(230, 60, 1),
			new Asteroid(288, 70, 1),
			new Asteroid(63, 126, 1),
			new Asteroid(116, 203, 1),
			new Asteroid(180, 140, 2)
		};
		return asteroids;
	}
	
	private Asteroid[] getLevel7Asteroids() {
		Asteroid[] asteroids = {
			new Asteroid(190, 50, 1),
			new Asteroid(160, 80, 1),
			new Asteroid(130, 110, 1),
			new Asteroid(100, 140, 1),
			new Asteroid(70, 170, 1),
			new Asteroid(170, 190, 1),
			new Asteroid(200, 160, 1),
			new Asteroid(230, 130, 1),
			new Asteroid(260, 100, 1)
		};
		
		return asteroids;
	}
	
	private Asteroid[] getLevel8Asteroids() {
		Asteroid[] asteroids = {
			new Asteroid(70, 190, 1),
			new Asteroid(70, 160, 1),
			new Asteroid(70, 130, 1),
			new Asteroid(70, 100, 1),
			new Asteroid(70, 70, 1),
			new Asteroid(100, 70, 1),
			new Asteroid(130, 70, 1),
			new Asteroid(160, 70, 1),
			new Asteroid(190, 70, 1),
			new Asteroid(220, 70, 1),
			new Asteroid(250, 70, 1)
		};
		
		return asteroids;
	}
	
}