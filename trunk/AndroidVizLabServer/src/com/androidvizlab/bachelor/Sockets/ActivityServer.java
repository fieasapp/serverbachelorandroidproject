package com.androidvizlab.bachelor.Sockets;

import com.androidvizlab.bachelor.Interface.SimpleObservable;
import java.net.ServerSocket;


public class ActivityServer extends SimpleObservable implements Runnable{
    
    // SERVER CONNECTION VARIABLES
    private int PORTNR = 1330;
    private ServerSocket server_socket = null;
    private ClientHandler client_handler = null;

    // MQTT CONNECTION VARIABLES
    private String brokerAddress = "localhost";
    private int brokerPort = 1883;
    
    //Server STATE
    private SERVERSTATE serverState = SERVERSTATE.SERVER_STATE_READY;
    
    //RUNNING
    private boolean continueRunning = true;
    
    public ActivityServer()
    {
        setServerState(SERVERSTATE.SERVER_STATE_READY);
    }
    
    public SERVERSTATE getServerState()
    {
        return serverState;
    }
    
    public void setServerState(SERVERSTATE state)
    {
        switch(state)
        {
            case SERVER_STATE_READY:
                serverState = SERVERSTATE.SERVER_STATE_READY;
                notifyObservers();
                break;
            case SERVER_STATE_RUNNING:
                serverState = SERVERSTATE.SERVER_STATE_RUNNING;
                notifyObservers();
                break;
            case SERVER_STATE_ACCEPTING:
                serverState = SERVERSTATE.SERVER_STATE_ACCEPTING;
                notifyObservers();
                break;
            case SERVER_STATE_STOP:
                serverState = SERVERSTATE.SERVER_STATE_STOP;
                notifyObservers();
                break;
        }
    }


    public void start()
    {
        try
        {
            server_socket = new ServerSocket(PORTNR);
            
            setServerState(SERVERSTATE.SERVER_STATE_RUNNING); //Set server state
            
            while(continueRunning)
            {
                System.out.println("Server: Waiting for client. . .");
                this.notifyObservers("Server: Waiting for client. . .");
                client_handler = new ClientHandler(server_socket.accept());
                System.out.println("Server: A Client has connected.");
                this.notifyObservers("Server: A Client has connected.");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println("Error: " + e);
            stop();
        }
    }

    public void stop()
    {
        try
        {
            setContinueRunning(false); //cancel while loop
            
            if(server_socket != null && server_socket.isBound())
            {
                server_socket.close();
            }
        
            setServerState(SERVERSTATE.SERVER_STATE_STOP); //Set server state
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Found an error in closing server socket...");
        }
    }
    
    //**** GETTERS AND SETTERS ****//

    public int getPORTNR() {
        return PORTNR;
    }

    public void setPORTNR(int PORTNR) {
        this.PORTNR = PORTNR;
        this.notifyObservers();
    }

    public String getBrokerAddress() {
        return brokerAddress;
    }

    public void setBrokerAddress(String brokerAddress) {
        this.brokerAddress = brokerAddress;
        this.notifyObservers();
    }

    public int getBrokerPort() {
        return brokerPort;
    }

    public void setBrokerPort(int brokerPort) {
        this.brokerPort = brokerPort;
        this.notifyObservers();
    }

    public boolean isContinueRunning() {
        return continueRunning;
    }

    public void setContinueRunning(boolean continueRunning) {
        this.continueRunning = continueRunning;
        this.notifyObservers();
    }
    
    //*** RUNNABLE INTERFACE ***//
    
    @Override
    public void run() 
    {
        start(); //
    }
    
    //*** SERVER STATE ENUM ***//
    
    public enum SERVERSTATE
    {
        SERVER_STATE_RUNNING(1000),
        SERVER_STATE_STOP(1001),
        SERVER_STATE_ACCEPTING(1002),
        SERVER_STATE_READY(1003);
        
        private int code;
        
        private SERVERSTATE(int code)
        {
            this.code = code;
        }
        
        public int getCode()
        {
            return code;
        }
        
        public String getValue()
        {
            String value = "";
            switch(code)
            {
                case 1000:
                    value = "Server is ready.";
                    break;
                case 1001:
                    value = "Server is running.";
                    break;
                case 1002:
                    value = "";
                    break;
                case 1003:
                    value = "Server has stop.";
                    break;
            }
            
            return value;
        }
    }
    
    //JUST FOR TESTING
    /*public static void main(String args[])
    {
        ActivityServer as = new ActivityServer();
        as.start();
    }*/
}
