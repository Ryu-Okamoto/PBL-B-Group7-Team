package G7team;

import java.io.Serializable;

public class Point implements Serializable{
	private static final long serialVersionUID = 1L;
	private double X;
	private double Y;
	
	public Point(double x,double y) {
		this.X = x;
		this.Y = y;
	}
	
	public double getX() {
		return X;
	}
	
	public double getY() {
		return Y;
	}
	
	public void setX(double x) {
		this.X = x;
	}
	
	public void setY(double y) {
		this.Y = y;
	}
	
	public Point add(Point point) {
		return new Point(this.X + point.getX(), this.Y + point.getY());
	}
	
	public double distance(Point point) {
		return Math.sqrt((this.X - point.getX())*(this.X - point.getX())
				+ (this.Y - point.getY())*(this.Y - point.getY()));
	}
}