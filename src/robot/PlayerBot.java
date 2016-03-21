package robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Random;

public class PlayerBot implements Runnable {
	
	private Random rand;

	@Override
	public void run() {
		rand = new Random();
		Move move = new Move();
		Robot r;
		try {
			r = new Robot();
			int lol = 0;
			move.fake = lol;
			Thread thread = new Thread(new FakeSearch(move));
			thread.start();
			//FakeSearch search = new FakeSearch(move);
			//search.run();
			while(true) {
				//System.out.println("bot: " + move.fake);
				int next = rand.nextInt(3);
				
				if(next == 0)
					r.keyPress(KeyEvent.VK_UP);
				else if(next == 1)
					r.keyPress(KeyEvent.VK_RIGHT);
				else if(next == 2)
					r.keyPress(KeyEvent.VK_LEFT);
			}
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
