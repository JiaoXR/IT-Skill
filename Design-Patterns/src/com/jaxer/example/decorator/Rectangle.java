package com.jaxer.example.decorator;

/**
 * 被装饰者1
 *
 * @author jaxer
 * date 03/04/2018
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Shape: Rectangle");
    }
}
