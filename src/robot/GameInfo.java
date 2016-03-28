package robot;

import java.awt.geom.Rectangle2D;

import entity.Asteroid;
import entity.Target;

public class GameInfo {
	public double rocketx;
	public double rockety;
	public double rocketdx;
	public double rocketdy;
	public double fuelSpent;
	public double singleRotation;
	public double singleThrustFuel;
	public Rectangle2D boundaryRect;
	public Target target;
	public Asteroid[] asteroids;
	
	public GameInfo(
			double rx,
			double ry,
			double rdx,
			double rdy,
			double fuelSpent,
			double singleRotation,
			double singleThrustFuel,
			Rectangle2D boundary,
			Target target,
			Asteroid[] asteroids) {
		rocketx = rx;
		rockety = ry;
		rocketdx = rdx;
		rocketdy = rdy;
		this.fuelSpent = fuelSpent;
		this.singleRotation = singleRotation;
		this.singleThrustFuel = singleThrustFuel;
		boundaryRect = boundary;
		this.target = target;
		this.asteroids = asteroids;
	}

	public GameInfo(double x, double y, double dx, double dy, double fuelSpent) {
		rocketx = x;
		rockety = y;
		rocketdx = dx;
		rocketdy = dy;
		this.fuelSpent = fuelSpent;
	}
}
