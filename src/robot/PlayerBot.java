package robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayerBot extends Thread {
	
	private MCTS thread;
	private Robot robot;
	private long searchTimeMillis = 50;
	private long wait = 25;
	private ConcurrentLinkedQueue<Move> queue;
	
	public PlayerBot( GameInfo info ) {
		
		TransitionModel tm = new TransitionModel
		(
			info.singleRotation, 
			info.singleThrustFuel, 
			searchTimeMillis/wait
		);
		
		queue = new ConcurrentLinkedQueue<>();
		
		thread = new MCTS(info, tm, searchTimeMillis, queue , this );
		
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
				
				if(queue.isEmpty()) {
					Thread.sleep(wait);
					continue;
				}
				
				Move move = queue.poll();
				
				for(int i = 0; i < move.numberOfTimes; i++) {
					if(move.keyCode != Integer.MIN_VALUE)
						robot.keyPress(move.keyCode);
					Thread.sleep(wait);
				}
			}
		} catch (InterruptedException e) {
			thread.interrupt();
		}
	}
}
