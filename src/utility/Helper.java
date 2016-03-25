package utility;


public class Helper {
	
	public static double rotateX(double angle, double x, double y) {
		return x * Math.cos(angle) - y * Math.sin(angle);
	}
	
	public static double rotateY(double angle, double x, double y) {
		return x * Math.sin(angle) + y * Math.cos(angle);
	}
	
	public static double calculateDistance( double x1, double y1, double x2, double y2) {
		double xDist = Math.max(x1, x2) - Math.min(x1, x2);
		double yDist = Math.max(y1, y2) - Math.min(y1, y2);
		return Math.sqrt(xDist * xDist + yDist * yDist);
	}
}
