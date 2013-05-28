package com.androidvizlab.bachelor.Interface;

/**
 * An interface implemented by Observers. Used in the observer-pattern
 * @author The Hive
 */
public interface Observer {
    public void update(DataChangeEvent<?> e);
    public void update(DataChangeEvent<?> e, Object obj);
}
