package com.androidvizlab.bachelor.Interface;

import java.util.ArrayList;

public interface IObservable {
    public void addObserver(Observer observer);
    public void addObservers(ArrayList<Observer> list);
    public void removeObserver(Observer observer);
    public void removeAllObservers();
    public void notifyObservers();
    public void notifyObservers(Object object);
}
