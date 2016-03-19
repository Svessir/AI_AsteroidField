package robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class PlayerBot implements Runnable{

	@Override
	public void run() {
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
				r.keyPress(KeyEvent.VK_UP);
			}
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
