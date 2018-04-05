package com.jaxer.example.abfactory.factory;

import com.jaxer.example.abfactory.color.Blue;
import com.jaxer.example.abfactory.color.Color;
import com.jaxer.example.abfactory.color.Green;
import com.jaxer.example.abfactory.color.Red;
import com.jaxer.example.abfactory.shape.Shape;

/**
 * Color工厂
 *
 * @author jaxer
 * date 04/04/2018
 */
public class ColorFactory extends AbstractFactory {
    @Override
    public Shape getShape(String shape) {
        return null;
    }

    @Override
    public Color getColor(String color) {
        if (color == null) {
            return null;
        }
        if (color.equalsIgnoreCase("red")) {
            return new Red();
        } else if (color.equalsIgnoreCase("green")) {
            return new Green();
        } else if (color.equalsIgnoreCase("blue")) {
            return new Blue();
        }

        return null;
    }
}
