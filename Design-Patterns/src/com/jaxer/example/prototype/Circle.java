package com.jaxer.example.prototype;

/**
 * @author jaxer
 * date 10/04/2018
 */
public class Circle extends Shape {

    public Circle() {
        type = "Circle";
    }

    @Override
    void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
