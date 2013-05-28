package com.androidvizlab.bachelor.Interface;

import java.util.ArrayList;

/**
 * An interface implemented by the observable objects. Used in the observer-pattern.
 * @author The Hive
 */
public interface IObservable {
    public void addObserver(Observer observer);
    public void addObservers(ArrayList<Observer> list);
    public void removeObserver(Observer observer);
    public void removeAllObservers();
    public void notifyObservers();
    public void notifyObservers(Object object);
}
