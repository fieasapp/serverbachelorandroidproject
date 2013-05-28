package com.androidvizlab.bachelor.Interface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This is a simple observable class that implements the IObservable interface.
 * Interested observers are added and updated when the state of this class-object are
 * changed.
 * @author The Hive
 */
public class SimpleObservable implements IObservable{

    private Set<Observer> observers;

    public SimpleObservable()
    {
        observers = new HashSet<Observer>();
    }

    /**
     * Adds an observer to a list
     * @param observer 
     */
    @Override
    public synchronized void addObserver(Observer observer) 
    {
        observers.add(observer);
    }

    /**
     * Adds a list of observers.
     * @param list 
     */
    @Override
    public synchronized void addObservers(ArrayList<Observer> list) 
    {

        if(list != null && list.size() > 0)
        {
            for(Observer obs : list)
            {
                observers.add(obs);
            }
        }
    }

    /**
     * Removes a given observer
     * @param observer 
     */
    @Override
    public synchronized void removeObserver(Observer observer) 
    {
        observers.remove(observer);
    }

    /**
     * Remove all observers from the observer list.
     */
    @Override
    public synchronized void removeAllObservers() 
    {
        observers.clear();
    }

    /**
     * Notifies observers on changes.
     */
    @Override
    public synchronized void notifyObservers() 
    {

        DataChangeEvent e = new DataChangeEvent(this);

        for(Observer obs : observers)
        {
            obs.update(e);
        }
    }

    /**
     * Notifies observers on changes. A optional object can be sent to the observers 
     * when changes happens.
     * @param object 
     */
    @Override
    public synchronized void notifyObservers(Object object) 
    {
        DataChangeEvent e = new DataChangeEvent(this);

        for(Observer obs : observers)
        {
            obs.update(e,object);
        }
    }
}
