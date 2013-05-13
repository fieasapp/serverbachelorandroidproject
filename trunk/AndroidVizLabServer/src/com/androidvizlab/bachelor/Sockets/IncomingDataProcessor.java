/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.androidvizlab.bachelor.Sockets;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author The Hive
 */
public class IncomingDataProcessor {
    
    
    private ObjectInputStream inputstream = null;
    private ObjectOutputStream outputstream = null;
    
    public IncomingDataProcessor(ObjectInputStream inputstream,
            ObjectOutputStream outputstream)
    {
        this.inputstream = inputstream;
        this.outputstream = outputstream;
    }
    
    public void processData(Object data)
    {
        
    
    }
    
    public void run()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                
            }
        }).start();
    }
}
