package robot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
	 * TransitionModel - Includes information of how the rocket moves.
	 * SINGLE_ROTATION - Defines how many radians the rocket rotates for each rotation action
	 * MOVEMENT_DELTA - Defines how far the rocket moves for each movement action (thrust or gravitational pull)
	 * FUEL_PER_THRUST - Defines how much fuel is consumed for each thrust action
	 */
	public class TransitionModel {
		public final double SINGLE_ROTATION;
		public final double MOVEMENT_DELTA;
		public final double FUEL_PER_THRUST;
		
		public TransitionModel(double singleRotation, double movement_delta, double fuelPerThrust) {
			SINGLE_ROTATION = singleRotation;
			MOVEMENT_DELTA = movement_delta;
			FUEL_PER_THRUST = fuelPerThrust;
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
			ArrayList<Action> actions = new ArrayList<>();
			actions.add(Action.ROTATE_LEFT);
			actions.add(Action.ROTATE_RIGHT);
			actions.add(Action.THRUST);
			return actions;
		}
		
		public State getSuccessorState(Action action) {
			return null;
		}
		
		public double evaluate() { return 0; }
		
		public Action getRandomLegalAction() { return null; }
		
		public void playAction( Action action ) {}
	}
	
	/**
	 * Node - A wrapper for a state within the search tree.
	 * A node includes a reference to it's parent node and 
	 * it's children nodes. 
	 */
	private class Node {
		private final State state;
		private final Action action;
		private ArrayList<Node> children;
		private ArrayList<Action> unexploredActions;
		private double eval;
		
		public Node(State state, Action action) {
			this.state = state;
			this.action = action;
			unexploredActions = state.getLegalActions();
		}
		
		public boolean isLeaf() { return children == null || children.isEmpty(); }
		
		public boolean isFullyExpanded() { return unexploredActions.isEmpty(); }
		
		public Node selectChild() {
			Node best = children.get(0);
			double bestEval = best.eval;
			
			for(Node child : children) {
				if(child.eval > bestEval) {
					best = child;
					bestEval = eval;
				}
			}
			
			return best;
		}
		
		public Node expand() {
			int index = rand.nextInt(unexploredActions.size());
			Action action = unexploredActions.remove(index);
			
			if(children == null) children = new ArrayList<Node>();
			
			Node child = new Node(state.getSuccessorState(action), action);
			children.add(child);
			return child;
		}
		
		public double simulate(int depth) {
			State currentState = new State(state);
			
			for(int i = 0; i < depth; i++)
				currentState.playAction(currentState.getRandomLegalAction());
			
			return currentState.evaluate(); 
		}
		
		public void update(double value) {}
		
		public Node bestChild() { return root.selectChild(); }
	}
	
	//private class OutOfTimeException extends RuntimeException {}
	
	private long searchTimeMillis = 500;
	private long startTime;
	private int maxDepth = 30;
	private World world;
	private Node root;
	private PlayerBot.Move move;
	private Random rand = new Random();
	
	public MCTS(GameInfo info, PlayerBot.Move move) {
		
		// static objects
		world = new World(info.targetx, info.targety);
		
		// initializing root
		root = new Node(
				new State(info.rocketx, info.rockety, info.rocketdx, info.rocketdy, info.fuelSpent), null);
		
		this.move = move;
	}

	@Override
	public void run() {
		while(true) {
			search();
			move.action = root.action;
		}
	}
	
	public void search() {
		startTime = System.currentTimeMillis();
		
		while(isTime()) {
			List<Node> visited = new LinkedList<Node>();
			
			// selectedNode
			Node node = selection(visited);
			
			// expansion
			node = node.expand();
			
			// simulation
			double value = node.simulate(maxDepth);
			
			// back propagation
			for(Node n : visited) {
				n.update(value);
			}
		}
		
		root = root.bestChild();
	}
	
	private boolean isTime() {
		return System.currentTimeMillis()- startTime < searchTimeMillis;
	}
	
	private Node selection(List<Node> visited) {
		Node currentNode = root;
		visited.add(root);
		
		while(!currentNode.isLeaf() && currentNode.isFullyExpanded()) {
			currentNode = currentNode.selectChild();
			visited.add(currentNode);
		}
		return currentNode;
	}
}
