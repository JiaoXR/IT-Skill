package com.jaxer.example.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jaxer
 * date 10/04/2018
 */
public class ShapeCache {
    private static Map<Integer, Shape> shapeMap = new HashMap<>();

    public static Shape getShape(Integer shapeId) {
        Shape shape = shapeMap.get(shapeId);
        return (Shape) shape.clone();
    }

    public static void loadCache() {
        Circle circle = new Circle();
        circle.setId(1);
        shapeMap.put(1, circle);

        Rectangle rectangle = new Rectangle();
        rectangle.setId(2);
        shapeMap.put(2, rectangle);

        Square square = new Square();
        square.setId(3);
        shapeMap.put(3, square);
    }
}
