package gameState;

import entity.Results;
import tileMap.Background;

public abstract class GameState {
	protected GameStateManager gsm;
	protected Background bg;
	protected boolean isAI;
	protected String name;
	
	public GameState(GameStateManager gsm, String name) {
		this.gsm = gsm;
		this.name = name;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract boolean isGameOver();
	public abstract Results getResults();
	public void setAI(boolean isAI) {this.isAI = isAI;}
	public String getName() { return name; }
}
