package ru.spbstu.main.shapes;

public class Triangle implements Polygon {
	private Point[] points;
	private Integer rotation;
	
	public Triangle(Point a, Point b, Point c, Integer rotation) {
		points = new Point[3];
		points[0] = a;
		points[1] = b;
		points[2] = c;
		this.rotation = rotation % 360;
	}
	
	@Override
	public float getArea() {
		float p = getPerimeter() / 2;
		return (float) Math.sqrt(p * (p - MyPoint.distance(points[0], points[1])) * 
									 (p - MyPoint.distance(points[1], points[2])) *
									 (p - MyPoint.distance(points[2], points[0])));
	}

	@Override
	public float getPerimeter() {
		return MyPoint.distance(points[0], points[1]) + MyPoint.distance(points[1], points[2]) 
			 + MyPoint.distance(points[0], points[2]);
	}

	@Override
	public int getRotation() {
		return rotation;
	}
	
	public void setRotation(Integer rotation) {
		this.rotation = rotation % 360;
	}
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Triangle (");
		for(int i = 0; i < points.length; i++) {
			sb.append("(").append(points[i].getX()).append("; ");
			sb.append(points[i].getY()).append(")");
		}
		sb.append("). rotation = ").append(getRotation());
		return sb.toString();
	}
}
