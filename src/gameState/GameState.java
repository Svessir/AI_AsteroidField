package gameState;

import entity.Results;
import tileMap.Background;

public abstract class GameState {
	protected GameStateManager gsm;
	protected Background bg;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void init();
	public abstract void init(Results r);
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract boolean isGameOver();
	public abstract Results getResults();
}
