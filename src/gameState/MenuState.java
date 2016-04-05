package gameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import entity.Results;
import main.Game;
import main.GamePanel;
import tileMap.Background;

public class MenuState extends GameState {
	
	private Background bg;
	
	private int currentChoice = 0;
	private String[] options = {
			"Start",
			"AI Start",
			"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		super(gsm, "Back");
		titleColor = new Color(128, 0, 0);
		titleFont = new Font("Century Gothic", Font.PLAIN, 28);
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
	public void init() {}

	@Override
	public void update() {
		bg.update();
	}

	@Override
	public void draw(Graphics2D g) {
		// draw bg
		bg.draw(g);
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Asteroid Field", GamePanel.WIDTH/2 - 80, GamePanel.HEIGHT/2 - 60);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			Color color = i == currentChoice ? Color.PINK : Color.RED;
			g.setColor(color);
			g.drawString(options[i], GamePanel.WIDTH/2 - 20, GamePanel.HEIGHT/2 + i * 15);
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setAi(false);
			gsm.setState(GameStateManager.LEVELSELECTIONSTATE);
		}
		if(currentChoice == 1) {
			gsm.setAi(true);
			gsm.setState(GameStateManager.LEVELSELECTIONSTATE);
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}

	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice = currentChoice > 0 ? currentChoice - 1 : options.length - 1;
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice = currentChoice < options.length - 1 ? currentChoice + 1 : 0;
		}
	}

	@Override
	public void keyReleased(int k) {}

	@Override
	public boolean isGameOver() { return false; }

	@Override
	public Results getResults() { return null; }

	@Override
	public void turnOffAi() {
		// TODO Auto-generated method stub
		
	}
}
