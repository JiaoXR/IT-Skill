package com.jaxer.example.observer;

/**
 * 观察者2
 *
 * @author jaxer
 * date 03/04/2018
 */
public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        this.subject = subject;
        this.subject.add(this);
    }

    @Override
    void update() {
        System.out.println("Octal String: "
                + Integer.toOctalString(subject.getState()));
    }
}
