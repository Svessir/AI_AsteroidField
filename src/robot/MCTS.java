package robot;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import utility.Helper;


public class MCTS implements Runnable {
	
	public enum Action {THRUST, ROTATE_LEFT, ROTATE_RIGHT, NOOP};
	
	/**
	 * World - includes the static objects in the game.
	 * States can reference this object for information
	 */
	private class World {
		public final double target_x;
		public final double target_y;
		public final Rectangle2D boundary;
		
		public World(double target_x, double target_y, Rectangle2D boundary) {
			this.target_x = target_x;
			this.target_y = target_y;
			this.boundary = boundary;
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
			State successor = new State(this);
			successor.playAction(action);
			return successor;
		}
		
		public double evaluate() { 
			return 112.0/(Helper.calculateDistance(x, y, world.target_x, world.target_y) + fuelSpent); 
		}
		
		public Action getRandomLegalAction() {
			int number = rand.nextInt(3);
			
			if(number == 0)
				return Action.THRUST;
			if(number == 1)
				return Action.ROTATE_RIGHT;
			if(number == 2)
				return Action.ROTATE_LEFT;
			
			return Action.NOOP;
		}
		
		public void playAction( Action action ) {
			
			if(action == Action.ROTATE_LEFT)
				rotate_left();
			else if(action == Action.ROTATE_RIGHT)
				rotate_right();
			else if(action == Action.THRUST)
				thrust();
			
			// else move by gravity pull
		}
		
		private void rotate_left() {
			rotate(-transitionModel.SINGLE_ROTATION);
		}
		
		private void rotate_right() {
			rotate(transitionModel.SINGLE_ROTATION);
		}
		
		private void rotate(double angle) {
			double oldDx = dx;
			dx = Helper.rotateX(angle, dx, dy);
			dy = Helper.rotateY(angle, oldDx, dy);
		}
		
		private void thrust() {
			fuelSpent += transitionModel.FUEL_PER_THRUST;
			move(dx, dy);
		}
		
		private void move(double dx, double dy) {
			x += dx;
			y += dy;
		}
		
		private void print() {
			System.out.println("x: " + x + " y: " + y);
		}
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
					bestEval = child.eval;
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
			
			eval = currentState.evaluate();
			return eval;
		}
		
		public void update(double value) {
			double sum = 0;
			for(Node child : children) {
				sum += child.eval;
			}
			eval = sum/children.size();
		}
		
		public Node bestChild() { return root.selectChild(); }
	}
	
	//private class OutOfTimeException extends RuntimeException {}
	
	private long searchTimeMillis;
	private long startTime;
	private int maxDepth = 30;
	private World world;
	private TransitionModel transitionModel;
	private Node root;
	private PlayerBot.Move move;
	private Random rand = new Random();
	
	public MCTS(GameInfo info, TransitionModel tm, long searchTimeMillis, PlayerBot.Move move) {
		
		// static objects
		world = new World(info.targetx, info.targety, info.boundaryRect);
		
		// initializing root
		root = new Node(
				new State
				(
					info.rocketx, 
					info.rockety,
					info.rocketdx * tm.NUMBER_OF_ACTIONS_PER_TRANSITION,
					info.rocketdy * tm.NUMBER_OF_ACTIONS_PER_TRANSITION,
					info.fuelSpent
				),
				null);
		
		transitionModel = tm;
		
		this.searchTimeMillis = searchTimeMillis;
		
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
		int numberE = 0;
		while(isTime()) {
			List<Node> visited = new LinkedList<Node>();
			
			// selectedNode
			Node node = selection(visited);
			
			// expansion
			node = node.expand();
			numberE++;
			
			// simulation
			double value = node.simulate(maxDepth);
			//System.out.println(value);
			
			// back propagation
			for(Node n : visited) {
				n.update(value);
			}
		}
		System.out.println(numberE);
		root = root.bestChild();
		root.state.print();
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
