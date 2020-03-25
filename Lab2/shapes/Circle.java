package ru.spbstu.main.shapes;

public class Circle implements Ellipse {
	private Point center;
	private Float radius;
	
	public Circle(Point point, Float radius) {
		if (radius <= 0) {
			throw new IllegalArgumentException("Radius cannot be negative");
		}

		this.setCenter(point);
		this.setRadius(radius);
	}
	
	@Override
	public float getLength() {
		return (float) (2 * Math.PI * radius);
	}

	@Override
	public float getArea() {
		return (float) (Math.PI * Math.pow(radius, 2));
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public Float getRadius() {
		return radius;
	}

	public void setRadius(Float radius) {
		this.radius = radius;
	}
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Circle (").append(center.getX()).append("; ").append(center.getY());
		sb.append("), radius = ").append(radius);
		return sb.toString();
	}
}
