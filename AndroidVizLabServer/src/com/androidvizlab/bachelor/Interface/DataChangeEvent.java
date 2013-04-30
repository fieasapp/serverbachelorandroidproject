package com.androidvizlab.bachelor.Interface;

/**
 * An event use in the Observer interface.
 * This class is a super class and every
 * subclass can declare the class type <T>
 * of the event source.
 * 
 * @author The Hive
 * @param <T> replace by the class type of the event source.
 */
public class DataChangeEvent<T> {
	
    private T source;

    public DataChangeEvent(T source)
    {
        this.source = source;
    }

    public T getSource()
    {
        return source;
    }
}
