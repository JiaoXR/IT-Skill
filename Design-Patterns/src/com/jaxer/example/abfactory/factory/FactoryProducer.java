package com.jaxer.example.abfactory.factory;

/**
 * 工厂的工厂
 *
 * @author jaxer
 * date 04/04/2018
 */
public class FactoryProducer {
    public static AbstractFactory getFactory(String choice) {
        if (choice == null) {
            return null;
        }

        if (choice.equalsIgnoreCase("shape")) {
            return new ShapeFactory();
        } else if (choice.equalsIgnoreCase("color")) {
            return new ColorFactory();
        }

        return null;
    }
}
