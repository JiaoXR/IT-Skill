package com.jaxer.example.proxy;

/**
 * 代理类
 *
 * @author jaxer
 * date 26/02/2018
 */
public class MyProxy implements ICalculator {

    private ICalculator iCalculator;

    public MyProxy() {
        iCalculator = new CalculatorImpl();
    }

    @Override
    public int add(int a, int b) {
        doSthBefore();
        int result = iCalculator.add(a, b);
        System.out.println("result = " + result);
        doSthAfter();

        return result;
    }

    private void doSthBefore() {
        System.out.println("doSthBefore");
    }

    private void doSthAfter() {
        System.out.println("doSthAfter");
    }
}
