package com.jaxer.example.observer;

/**
 * 测试类
 *
 * @author jaxer
 * date 03/04/2018
 */
public class TestObserver {
    public static void main(String[] args) {
        Subject subject = new Subject();

        new BinaryObserver(subject);
        new OctalObserver(subject);
        new HexObserver(subject);

        System.out.println("First state change: 15");
        subject.setState(15);
        System.out.println("————————————————————————————");
        System.out.println("Second state change: 10");
        subject.setState(10);
    }
}
