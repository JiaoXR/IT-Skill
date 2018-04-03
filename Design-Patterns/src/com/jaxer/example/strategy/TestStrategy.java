package com.jaxer.example.strategy;

/**
 * 测试类
 *
 * @author jaxer
 * date 02/04/2018
 */
public class TestStrategy {
    public static void main(String[] args) {
        Context context = new Context(new OperationAdd());
        System.out.println("5 + 2 = " + context.executeStrategy(5, 2));

        context = new Context(new OperationSub());
        System.out.println("5 - 2 = " + context.executeStrategy(5, 2));

        context = new Context(new OperationMulti());
        System.out.println("5 * 2 = " + context.executeStrategy(5, 2));
    }
}
