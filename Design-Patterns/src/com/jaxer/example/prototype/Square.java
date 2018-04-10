package com.jaxer.example.prototype;

/**
 * @author jaxer
 * date 10/04/2018
 */
public class Square extends Shape {

    public Square() {
        type = "Square";
    }

    @Override
    void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
