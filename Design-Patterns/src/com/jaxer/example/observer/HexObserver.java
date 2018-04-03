package com.jaxer.example.observer;

/**
 * 观察者3
 *
 * @author jaxer
 * date 03/04/2018
 */
public class HexaObserver extends Observer {

    public HexaObserver(Subject subject) {
        this.subject = subject;
        this.subject.add(this);
    }

    @Override
    void update() {
        System.out.println("Hex String: "
                + Integer.toOctalString(subject.getState()));
    }
}
