package com.androidvizlab.bachelor.Interface;

import runtimetest.ExternalProcessHandler;

/**
 *
 * @author The Hive
 */
public class ExternalProcessHandlerDataChangeEvent extends DataChangeEvent<ExternalProcessHandler>{
    
    public ExternalProcessHandlerDataChangeEvent(ExternalProcessHandler source)
    {
        super(source);
    }
    
    @Override
    public ExternalProcessHandler getSource()
    {
        return super.getSource();
    }
}
