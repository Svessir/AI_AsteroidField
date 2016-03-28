package utility;

public class Vector2D {
	public double x;
	public double y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void convertToUnitVector() {
		double length = getLength();
		x /= length;
		y /= length;
	}
	
	public void addVector(Vector2D o) { 
		x += o.x;
		y += o.y;
	}
	
	public void mulitplyBy(double multiplier) {
		x *= multiplier;
		y *= multiplier;
	}
	
	public double getLength() { return Math.sqrt(x*x + y*y); }
}