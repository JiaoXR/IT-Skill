package com.jaxer.example.abfactory.factory;

import com.jaxer.example.abfactory.color.Color;
import com.jaxer.example.abfactory.shape.Circle;
import com.jaxer.example.abfactory.shape.Rectangle;
import com.jaxer.example.abfactory.shape.Shape;
import com.jaxer.example.abfactory.shape.Square;

/**
 * Shape工厂
 *
 * @author jaxer
 * date 04/04/2018
 */
public class ShapeFactory extends AbstractFactory {
    @Override
    public Shape getShape(String shape) {
        if (shape == null) {
            return null;
        }
        if (shape.equalsIgnoreCase("circle")) {
            return new Circle();
        } else if (shape.equalsIgnoreCase("rectangle")) {
            return new Rectangle();
        } else if (shape.equalsIgnoreCase("square")) {
            return new Square();
        }

        return null;
    }

    @Override
    public Color getColor(String color) {
        return null;
    }
}
