package com.jaxer.example.facade;

/**
 * 测试类
 *
 * @author jaxer
 * date 07/04/2018
 */
public class TestFacade {
    public static void main(String[] args) {
        ShapeMaker shapeMaker = new ShapeMaker();

        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
        shapeMaker.drawSquare();
    }
}
