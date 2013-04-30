package com.androidvizlab.bachelor.Interface;

/**
 * 
 * @author The Hive
 */
public interface Observer {
    public void update(DataChangeEvent<?> e);
    public void update(DataChangeEvent<?> e, Object obj);
}
