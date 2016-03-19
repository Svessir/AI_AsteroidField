package robot;
import javax.swing.text.Position;

import gameState.GameStateManager;

public class FakeSearch implements Runnable {
    private Move move;
    
	public FakeSearch(Move move) {
		this.move = move;
	}
	
	private class Pos implements Position {
		int x;
		int y;
		@Override
		public int getOffset() {
			return 0;
		}
	}
	
	@Override
	public void run() {
		while(true) {
			int j = 0;
			long time = System.currentTimeMillis();
			for(int i = 0; i < 810000; i++) {
				j++;
				if(System.currentTimeMillis() - time > 500) break;
				State state = new State(new Pos(), Orientation.NORTH , false, null);
				for(String action : state.legalActions()) {
					State s = new State(state);
				}
			}
			move.fake++;
			//System.out.println("search: " + move.fake);
		}
	}

}
