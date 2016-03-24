package robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class PlayerBot implements Runnable {
	
	public class Move {
		public MCTS.Action action;
		
		public Move(MCTS.Action action) {
			this.action = action;
		}
	}
	
	private Move move;
	private Thread thread;
	private Robot robot;
	
	public PlayerBot( GameInfo info ) {
		move = new Move(MCTS.Action.NOOP);
		thread = new Thread(new MCTS(info, move));
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			thread.start();
			
			while(true) {
				if(move.action == MCTS.Action.ROTATE_LEFT)
					robot.keyPress(KeyEvent.VK_LEFT);
				else if(move.action == MCTS.Action.ROTATE_RIGHT)
					robot.keyPress(KeyEvent.VK_RIGHT);
				else if(move.action == MCTS.Action.THRUST)
					robot.keyPress(KeyEvent.VK_UP);
				
				Thread.sleep(25);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
