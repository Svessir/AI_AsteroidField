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
	private MCTS thread;
	private Robot robot;
	private long searchTimeMillis = 500;
	private long wait = 25;
	
	public PlayerBot( GameInfo info ) {
		move = new Move(MCTS.Action.NOOP);
		TransitionModel tm = new TransitionModel
		(
			info.singleRotation, 
			info.singleThrustFuel, 
			searchTimeMillis/wait
		);
		
		thread = new MCTS(info, tm, searchTimeMillis, move );
		
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
			
			while(!Thread.currentThread().isInterrupted()) {
				if(move.action == MCTS.Action.ROTATE_LEFT)
					robot.keyPress(KeyEvent.VK_LEFT);
				else if(move.action == MCTS.Action.ROTATE_RIGHT)
					robot.keyPress(KeyEvent.VK_RIGHT);
				else if(move.action == MCTS.Action.THRUST)
					robot.keyPress(KeyEvent.VK_UP);
				
				Thread.sleep(wait);
			}
		} catch (InterruptedException e) {
			thread.interrupt();
		}
	}

}
