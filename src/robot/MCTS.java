package robot;

public class MCTS {
	
	private class World {
		public double target_x;
		public double target_y;
	}
	
	private World w = new World();
	
	private class State {
		public double x;
		public double y;
		public double dx;
		public double dy;
		public double fuelSpent;
		
		public State (double x, double y, double dx, double dy, double fuelSpent) {
			this.x = x;
			this.y = y;
			this.dx = dx;
			this.dy = dy;
			this.fuelSpent = fuelSpent;
		}
	}
	
	private class Node {
		State state;
		Node parent;
	}

}
