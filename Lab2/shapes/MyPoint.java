package ru.spbstu.main.shapes;

public class MyPoint implements Point {
	private float x;
	private float y;

	public MyPoint(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public static float distance(Point a, Point b) {
		return (float) Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Point (").append(getX()).append("; ").append(getY()).append(")");
		return sb.toString();
	}
}
