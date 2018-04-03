package com.jaxer.example.strategy;

/**
 * 策略1
 *
 * @author jaxer
 * date 02/04/2018
 */
public class OperationAdd implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}
