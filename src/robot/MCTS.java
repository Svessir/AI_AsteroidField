package robot;

import java.util.ArrayList;
import java.util.Random;


public class MCTS implements Runnable {
	
	public enum Action {THRUST, ROTATE_LEFT, ROTATE_RIGHT, NOOP};
	
	/**
	 * World - includes the static objects in the game.
	 * States can reference this object for information
	 */
	private class World {
		public final double target_x;
		public final double target_y;
		
		public World(double target_x, double target_y) {
			this.target_x = target_x;
			this.target_y = target_y;
		}
	}
	
	/**
	 *  State - The state of the rocket within the world
	 *  - includes it's position and direction vector along with
	 *  it's total fuel consumption.
	 */
	private class State {
		// position
		public double x;
		public double y;
		
		// direction vector
		public double dx;
		public double dy;
		
		// fuel
		public double fuelSpent;
		
		public State (double x, double y, double dx, double dy, double fuelSpent) {
			this.x = x;
			this.y = y;
			this.dx = dx;
			this.dy = dy;
			this.fuelSpent = fuelSpent;
		}
		
		public State(State s) {
			x = s.x;
			y = s.y;
			dx = s.dx;
			dy = s.dy;
			fuelSpent = s.fuelSpent;
		}
		
		public ArrayList<Action> getLegalActions() {
			return null;
		}
		
		public State getSuccessorState(Action action) {
			return null;
		}
		
		public double evaluate() { return 0; }
		
		// playout (depth)
	}
	
	/**
	 * Node - A wrapper for a state within the search tree.
	 * A node includes a reference to it's parent node and 
	 * it's children nodes. 
	 */
	private class Node {
		private final State state;
		private final Node parent;
		private ArrayList<Node> children;
		private ArrayList<Action> unexploredActions;
		private double eval;
		
		public Node(State state, Node parent) {
			this.state = state;
			this.parent = parent;
		}
		
		public boolean isLeaf() { return children.isEmpty(); }
	}
	
	private class OutOfTimeException extends RuntimeException {}
	
	private long searchTimeMillis = 500;
	private long startTime;
	private int maxDepth = 30;
	private World world;
	private Node root;
	private PlayerBot.Move move;
	
	public MCTS(GameInfo info, PlayerBot.Move move) {
		
		// static objects
		world = new World(info.targetx, info.targety);
		
		// initializing root
		root = new Node(
				new State(info.rocketx, info.rockety, info.rocketdx, info.rocketdy, info.fuelSpent), 
				null);
		
		this.move = move;
	}

	@Override
	public void run() {
		while(true) {
			Action newAction = search();
			move.action = newAction;
			// fix root
		}
	}
	
	public Action search() {
		startTime = System.currentTimeMillis();
		
		while(isTime()) {
			
			// selectedNode
			// expansion
			// simulation
			// back propagation
			
		}
		
		return null;
	}
	
	private boolean isTime() {
		return System.currentTimeMillis()- startTime < searchTimeMillis;
	}
	
	// selection
	// expansion
	// simulation
	// back propagation
}
