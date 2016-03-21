package gameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entity.Results;

public class ResultState extends GameState {
	Results results;
	
	public ResultState(GameStateManager gsm) {
		super(gsm, "");
	}

	@Override
	public void init() {}
	
	public void init(Results r) { results = r; }

	@Override
	public void update() {}

	@Override
	public void draw(Graphics2D g) {
		if(results == null) return;
		results.draw(g);
		
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub
		if(k == KeyEvent.VK_ENTER)
			gsm.setState(GameStateManager.MENUSTATE);
		
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Results getResults() { return null; }

}
