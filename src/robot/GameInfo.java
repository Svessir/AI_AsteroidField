package robot;

public class GameInfo {
	public double rocketx;
	public double rockety;
	public double rocketdx;
	public double rocketdy;
	public double targetx;
	public double targety;
	public double fuelSpent;
	// public ArrayList of asteroids
	
	public GameInfo(double rx, double ry, double rdx, double rdy, double tx, double ty, double fuelSpent) {
		rocketx = rx;
		rockety = ry;
		rocketdx = rdx;
		rocketdy = rdy;
		targetx = tx;
		targety = ty;
		this.fuelSpent = fuelSpent;
	}
}
