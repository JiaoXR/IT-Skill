package com.jaxer.example.factory;

/**
 * 实现类3
 *
 * @author jaxer
 * date 04/04/2018
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
