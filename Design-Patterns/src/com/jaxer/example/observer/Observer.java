package com.jaxer.example.observer;

/**
 * 观察者父类
 *
 * @author jaxer
 * date 03/04/2018
 */
public abstract class Observer {
    protected Subject subject;
    abstract void update();
}
