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
}