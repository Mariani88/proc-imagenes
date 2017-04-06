package untref.controllers;

public class Point {
	private double x;
	private double y;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Boolean isMenor(Point point) {

		return ((this.x < point.getX()) && (this.y < point.getY()));
	}

}
