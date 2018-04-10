package com.jaxer.example.prototype;

/**
 * @author jaxer
 * date 10/04/2018
 */
public class Rectangle extends Shape {

    public Rectangle() {
        type = "Rectangle";
    }

    @Override
    void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
