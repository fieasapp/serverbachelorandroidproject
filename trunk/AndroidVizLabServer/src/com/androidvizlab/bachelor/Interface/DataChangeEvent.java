package com.androidvizlab.bachelor.Interface;

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
