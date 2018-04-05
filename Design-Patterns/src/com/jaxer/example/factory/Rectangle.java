package com.jaxer.example.factory;

/**
 * 实现类1
 *
 * @author jaxer
 * date 04/04/2018
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
