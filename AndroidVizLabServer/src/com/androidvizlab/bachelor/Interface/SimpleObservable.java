package com.androidvizlab.bachelor.Interface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author The Hive
 */
public class SimpleObservable implements IObservable{

    private Set<Observer> observers;

    public SimpleObservable()
    {
        observers = new HashSet<Observer>();
    }

    @Override
    public synchronized void addObserver(Observer observer) 
    {
        observers.add(observer);
    }

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

    @Override
    public synchronized void removeObserver(Observer observer) 
    {
        observers.remove(observer);
    }

    @Override
    public synchronized void removeAllObservers() 
    {
        observers.clear();
    }

    @Override
    public synchronized void notifyObservers() 
    {

        DataChangeEvent e = new DataChangeEvent(this);

        for(Observer obs : observers)
        {
            obs.update(e);
        }
    }

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
