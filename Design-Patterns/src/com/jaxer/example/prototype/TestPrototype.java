package com.jaxer.example.prototype;

/**
 * 测试类
 *
 * @author jaxer
 * date 10/04/2018
 */
public class TestPrototype {
    public static void main(String[] args) {
        ShapeCache.loadCache();

        Shape shape = ShapeCache.getShape(1);
        shape.draw();

        shape = ShapeCache.getShape(2);
        shape.draw();

        shape = ShapeCache.getShape(3);
        shape.draw();
    }
}
