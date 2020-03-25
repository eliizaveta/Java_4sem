package ru.spbstu.main;

import ru.spbstu.main.shapes.Shape;
import ru.spbstu.main.shapes.Triangle;
import ru.spbstu.main.shapes.Circle;
import ru.spbstu.main.shapes.MyPoint;
import ru.spbstu.main.shapes.Rectangle;

public class Main {
    public static void main(String [] args) {
        Shape [] shapes = {
        		new Circle(new MyPoint(0, 0), 7.0F),
        		new Circle(new MyPoint(4, 0), 2.0F),
        		new Circle(new MyPoint(4, 0), 6.0F),
        		new Circle(new MyPoint(4, 0), 5.0F),
        		new Triangle(new MyPoint(0, 0), new MyPoint(0, 0), new MyPoint(0, 0), 0),
        		new Triangle(new MyPoint(1, 1), new MyPoint(2, 2), new MyPoint(3, 3), 0),
        		new Triangle(new MyPoint(-2, 0), new MyPoint(2, 4), new MyPoint(4, 0), 45),
        		new Rectangle(new MyPoint(0, 0), new MyPoint(0, 5), new MyPoint(0, 5), new MyPoint(0, 0), 0),
        		new Rectangle(new MyPoint(0, 0), new MyPoint(0, 10), new MyPoint(100, 10), new MyPoint(100, 0), 180),
        		new Rectangle(new MyPoint(1, 1), new MyPoint(1, 10), new MyPoint(2, 10), new MyPoint(2, 1), 0),
        };

        for(int i = 0; i < shapes.length; i++) {
        	System.out.println(shapes[i]);
        	System.out.println(shapes[i].getArea());
        }

        System.out.println();
        System.out.println("___________________MAX__________________");
		System.out.println();
        System.out.println(findMaxArea(shapes));
    }
    
    private static Shape findMaxArea(Shape [] shapes) {
    	float max = 0;
    	int indexMax = 0;
    	for(int i = 0; i < shapes.length; i++) {
    		float currentArea = shapes[i].getArea();
    		if(max < currentArea) {
    			max = currentArea;
    			indexMax = i;
    		}
    	}
    	return shapes[indexMax];
    }
}
