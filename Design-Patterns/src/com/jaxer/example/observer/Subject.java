package com.jaxer.example.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject
 *
 * @author jaxer
 * date 03/04/2018
 */
public class Subject {
    private List<Observer> observerList = new ArrayList<>();
    private int state;

    public void add(Observer observer) {
        observerList.add(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : observerList) {
            observer.update();
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }
}
