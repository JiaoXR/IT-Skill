package com.jaxer.example.decorator;

/**
 * 装饰器实现类1
 *
 * @author jaxer
 * date 04/04/2018
 */
public class RedShapeDecorator extends ShapeDecorator {
    public RedShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        setRedBorder(decoratedShape);
    }

    private void setRedBorder(Shape decoratoredShape) {
        System.out.println("Border Color: Red");
    }
}
