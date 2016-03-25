package robot;

import java.awt.geom.Rectangle2D;

public class GameInfo {
	public double rocketx;
	public double rockety;
	public double rocketdx;
	public double rocketdy;
	public double targetx;
	public double targety;
	public double fuelSpent;
	public double singleRotation;
	public double singleThrustFuel;
	public Rectangle2D boundaryRect;
	
	// public ArrayList of asteroids
	
	public GameInfo(
			double rx,
			double ry,
			double rdx,
			double rdy,
			double tx,
			double ty,
			double fuelSpent,
			double singleRotation,
			double singleThrustFuel,
			Rectangle2D boundary) {
		rocketx = rx;
		rockety = ry;
		rocketdx = rdx;
		rocketdy = rdy;
		targetx = tx;
		targety = ty;
		this.fuelSpent = fuelSpent;
		this.singleRotation = singleRotation;
		this.singleThrustFuel = singleThrustFuel;
		boundaryRect = boundary;
	}
}
