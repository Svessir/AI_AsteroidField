package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import entity.Results;
import tileMap.Background;

public class LevelSelectionState extends GameState{
	
	private int currentChoice = 0;
	private ArrayList<String> levels;
	private Font font;
	
	public LevelSelectionState(GameStateManager gsm) {
		super(gsm, "");
		
		font = new Font("Arial", Font.PLAIN, 12);
		
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
		currentChoice = 0;
		levels = gsm.getLevelNames();
	}

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		
		g.setFont(font);
		for(int i = 0; i < levels.size(); i++) {
			Color color = i == currentChoice ? Color.PINK : Color.RED;
			g.setColor(color);
			g.drawString(levels.get(i), 145, 20 + i * 15);
		}
	}
	
	public void select() {
		gsm.setAi(isAI);
		if(currentChoice == 0)
			gsm.setState(GameStateManager.MENUSTATE);
		else if(currentChoice == 1)
			gsm.setState(GameStateManager.LEVEL1STATE);
	}

	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice = currentChoice > 0 ? currentChoice - 1 : levels.size() - 1;
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice = currentChoice < levels.size() - 1 ? currentChoice + 1 : 0;
		}
	}

	@Override
	public void keyReleased(int k) {}

	@Override
	public boolean isGameOver() {
		return false;
	}

	@Override
	public Results getResults() { return null; }
}
