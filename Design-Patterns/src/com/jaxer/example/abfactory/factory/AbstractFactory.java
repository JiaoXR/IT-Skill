package com.jaxer.example.abfactory.factory;

import com.jaxer.example.abfactory.color.Color;
import com.jaxer.example.abfactory.shape.Shape;

/**
 * 抽象工厂
 *
 * @author jaxer
 * date 04/04/2018
 */
public abstract class AbstractFactory {
    public abstract Shape getShape(String shape);

    public abstract Color getColor(String color);
}
