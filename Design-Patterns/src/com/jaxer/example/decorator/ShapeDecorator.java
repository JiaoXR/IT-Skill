package com.jaxer.example.decorator;

/**
 * 装饰器抽象类
 *
 * @author jaxer
 * date 03/04/2018
 */
public abstract class ShapeDecorator implements Shape {

    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public void draw() {
        decoratedShape.draw();
    }
}
