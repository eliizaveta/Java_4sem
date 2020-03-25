package ru.spbstu.main.shapes;

public class Rectangle implements Polygon {
	private Point[] points;
	private Integer rotation;
	
	public Rectangle(Point a, Point b, Point c, Point d, Integer rotation) {
		points = new Point[4];
		points[0] = a;
		points[1] = b;
		points[2] = c;
		points[3] = d;
		this.rotation = rotation % 360;
	}
		
	@Override
	public float getArea() {
		return MyPoint.distance(points[0], points[1]) * MyPoint.distance(points[1], points[2]);
	}
	
	@Override
	public int getRotation() {
		return rotation;
	}
	
	@Override
	public float getPerimeter() {
		return 2 * (MyPoint.distance(points[0], points[1]) + MyPoint.distance(points[1], points[2]));
	}

	public void setRotation(Integer rotation) {
		this.rotation = rotation % 360;
	}

	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Rectangle (");
		for(int i = 0; i < points.length; i++) {
			sb.append("(").append(points[i].getX()).append("; ");
			sb.append(points[i].getY()).append(")");
		}
		sb.append("). rotation = ").append(getRotation());
		return sb.toString();
	}
}
