package com.jaxer.example.proxy;

/**
 * 实现类
 *
 * @author jaxer
 * date 26/02/2018
 */
public class CalculatorImpl implements ICalculator {

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
