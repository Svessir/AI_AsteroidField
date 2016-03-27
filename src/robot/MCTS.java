package robot;

import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import entity.Target;
import utility.Helper;


public class MCTS extends Thread {
	
	public enum Action {THRUST, ROTATE_LEFT, ROTATE_RIGHT, NOOP};
	
	/**
	 * World - includes the static objects in the game.
	 * States can reference this object for information
	 */
	private class World {
		public final Target target;
		public final Rectangle2D boundary;
		
		public World(Target target, Rectangle2D boundary) {
			this.target = target;
			this.boundary = boundary;
		}
	}
	
	/**
	 *  State - The state of the rocket within the world
	 *  - includes it's position and direction vector along with
	 *  it's total fuel consumption.
	 */
	public class State {
		// position
		public double x;
		public double y;
		
		// direction vector
		public double dx;
		public double dy;
		
		// fuel
		public double fuelSpent;
		
		private State (double x, double y, double dx, double dy, double fuelSpent) {
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
			
			if(canThrust())
				actions.add(Action.THRUST);
			
			return actions;
		}
		
		public State getSuccessorState(Action action) {
			State successor = new State(this);
			successor.playAction(action);
			return successor;
		}
		
		public double evaluate() { 
			return (initialDistance - Helper.calculateDistance(x, y, world.target.getX(), world.target.getY()))/fuelSpent; 
		}
		
		public Action getRandomLegalAction() {
			int number = rand.nextInt(3);
			
			if(number == 0 && canThrust())
				return Action.THRUST;
			if(number == 1)
				return Action.ROTATE_RIGHT;
			if(number == 2)
				return Action.ROTATE_LEFT;
			
			return Action.NOOP;
		}
		
		public void playAction( Action action ) {
			
			if(action == Action.THRUST)
				thrust();
			else if(action == Action.ROTATE_LEFT)
				rotate_left();
			else if(action == Action.ROTATE_RIGHT)
				rotate_right();
			
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
		
		@SuppressWarnings("unused")
		private void print() {
			System.out.println("x: " + x + " y: " + y);
		}
		
		private boolean canThrust() {
			double ox = x, oy= y;
			move(dx, dy);
			boolean canThrust = world.boundary.contains(x, y);
			x = ox; y = oy;
			return canThrust;
		}
		
		public boolean isTerminal() {
			return world.target.getRectangle().contains(x, y);
		}
		
		public double getErrorBetweenStates(State s) {
			double error = 0.0;
			error += Math.max(x, s.x) - Math.min(x, s.x);
			error += Math.max(y, s.y) - Math.min(y, s.y);
			error += Math.max(dx, s.dx) - Math.min(dx, s.dx);
			error += Math.max(dy, s.dy) - Math.min(dy, s.dy);
			return error;
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
			return bestChild();
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
			int i;
			for(i = 0; i < depth; i++) {
				currentState.playAction(currentState.getRandomLegalAction());
				//currentState.isTerminal();
				if(currentState.isTerminal()) break; 
			}
			
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
		
		public Node bestChild() {
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
	}
	
	private long searchTimeMillis;
	private long startTime;
	private double initialDistance;
	private int maxDepth = 1000;
	private World world;
	private TransitionModel transitionModel;
	private Node root;
	private Random rand = new Random();
	private ConcurrentLinkedQueue<Move> queue;
	
	public MCTS(GameInfo info, TransitionModel tm, long searchTimeMillis, ConcurrentLinkedQueue<Move> queue, PlayerBot bot) {
		
		// static objects
		world = new World(info.target, info.boundaryRect);
		
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
		
		this.queue = queue;
		
		initialDistance = Helper.calculateDistance(info.rocketx, info.rockety, world.target.getX(), info.target.getY());
	}

	@Override
	public void run() {
		while(!isInterrupted()) {
			search();
			
			if(root.action == Action.THRUST)
				queue.add(new Move(KeyEvent.VK_UP, (int)transitionModel.NUMBER_OF_ACTIONS_PER_TRANSITION));
			else if(root.action == Action.ROTATE_LEFT)
				queue.add(new Move(KeyEvent.VK_LEFT, (int)transitionModel.NUMBER_OF_ACTIONS_PER_TRANSITION));
			else if(root.action == Action.ROTATE_RIGHT)
				queue.add(new Move(KeyEvent.VK_RIGHT, (int)transitionModel.NUMBER_OF_ACTIONS_PER_TRANSITION));
			/*else
				queue.add(new Move(Integer.MIN_VALUE, (int)transitionModel.NUMBER_OF_ACTIONS_PER_TRANSITION));*/
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
		//root.state.print();
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
