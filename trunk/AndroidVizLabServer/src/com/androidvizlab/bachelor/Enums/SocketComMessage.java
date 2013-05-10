package com.androidvizlab.bachelor.Enums;

import java.io.Serializable;

/**
 *
 * @author The Hive
 */
public class SocketComMessage 
implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public enum SocketMessage
    {
        GET_OPTIONSFILE,
        GET_RESULT_DATA,
        PROCESS_STARTED,
        PROCESS_DONE,
        PROCESS_INPROGRESS
    }
    
    private SocketMessage message = null;

    public SocketMessage getMessage()
    {
        return message;
    }
    
    public void setMessage(SocketMessage message)
    {
        this.message = message;
    }
}