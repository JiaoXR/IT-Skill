package com.jaxer.example.facade;

/**
 * 实现类1
 *
 * @author jaxer
 * date 07/04/2018
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Circle::draw()");
    }
}
