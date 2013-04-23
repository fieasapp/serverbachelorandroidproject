package com.androidvizlab.bachelor.Interface;

public interface Observer {
    public void update(DataChangeEvent<?> e);
    public void update(DataChangeEvent<?> e, Object obj);
}
