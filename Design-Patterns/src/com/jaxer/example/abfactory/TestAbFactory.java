package com.jaxer.example.abfactory;

import com.jaxer.example.abfactory.color.Color;
import com.jaxer.example.abfactory.factory.AbstractFactory;
import com.jaxer.example.abfactory.factory.FactoryProducer;
import com.jaxer.example.abfactory.shape.Shape;

/**
 * 测试抽象工厂
 *
 * @author jaxer
 * date 04/04/2018
 */
public class TestAbFactory {
    public static void main(String[] args) {
        AbstractFactory shapeFactory = FactoryProducer.getFactory("shape");
        Shape shape1 = shapeFactory.getShape("circle");
        shape1.draw();

        Shape shape2 = shapeFactory.getShape("rectangle");
        shape2.draw();

        Shape shape3 = shapeFactory.getShape("square");
        shape3.draw();

        System.out.println("---------------");

        AbstractFactory colorFactory = FactoryProducer.getFactory("color");
        Color color1 = colorFactory.getColor("red");
        color1.fill();

        Color color2 = colorFactory.getColor("green");
        color2.fill();

        Color color3 = colorFactory.getColor("blue");
        color3.fill();
    }
}
