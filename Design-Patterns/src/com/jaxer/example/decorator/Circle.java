package com.jaxer.example.decorator;

/**
 * 被装饰者2
 *
 * @author jaxer
 * date 03/04/2018
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Shape: Circle");
    }
}
