package com.androidvizlab.bachelor.Sockets;

import com.androidvizlab.bachelor.Interface.Observer;
import com.androidvizlab.bachelor.Interface.SimpleObservable;
import java.net.ServerSocket;
import javax.swing.JOptionPane;

/**
 * This is the main socket server. Clients that connects to the server socket 
 * is passed to a client handler and is executed on a different thread.
 * @author TheHive
 */
public class ActivityServer extends SimpleObservable implements Runnable{
    
    // SERVER CONNECTION VARIABLES
    private int PORTNR = 1330;
    private ServerSocket serverSocket = null;
    private ClientHandler clientHandler = null; //Handles and accepts clients  connecting to the server
    
    // MQTT CONNECTION VARIABLES
    private String brokerAddress = "localhost";
    private int brokerPort = 1883;
    
    //READING AND WRITING TO FILE VARIABLES
    private String  optionsFilePath = "";
    private String externalPrgrmPath = "";
    private String calibrationFilePath = "";
    
    //Server STATE
    private ServerState serverState = ServerState.SERVER_STATE_READY;
    
    //RUNNING
    private boolean continueRunning = true;
    
    private Observer clientObserver = null;
    
    public ActivityServer()
    {
        setServerState(ServerState.SERVER_STATE_READY);
    }
    
    /**
     * Describe the state of the server
     * @return an enum containing the state of the server
     */
    public ServerState getServerState()
    {
        return serverState;
    }
    
    /**
     * Sets the state of the server.
     * @param state an enum use to determine the state of the server
     */
    public void setServerState(ServerState state)
    {
        switch(state)
        {
            case SERVER_STATE_READY:
                serverState = ServerState.SERVER_STATE_READY;
                notifyObservers();
                break;
            case SERVER_STATE_RUNNING:
                serverState = ServerState.SERVER_STATE_RUNNING;
                notifyObservers();
                break;
            case SERVER_STATE_ACCEPTING:
                serverState = ServerState.SERVER_STATE_ACCEPTING;
                notifyObservers();
                break;
            case SERVER_STATE_STOP:
                serverState = ServerState.SERVER_STATE_STOP;
                notifyObservers();
                break;
        }
    }


    /**
     * Create a server socket with a given port number and continously listens to 
     * incoming connection from the clients.
     */
    public void start()
    {
        try
        {
            serverSocket = new ServerSocket(PORTNR);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Could not create socket!, check that port is available.");
            e.printStackTrace();
            stop();
        }    
        
        setContinueRunning(true);
        
        setServerState(ServerState.SERVER_STATE_RUNNING); //Set server state
            
        while(continueRunning)
        {
            try{
                
                if(serverSocket.isClosed()|| serverSocket == null)
                {
                    continue;
                }
                
                System.out.println("Server: Waiting for client. . .");
                this.notifyObservers("Server: Waiting for client. . .");
                        
                clientHandler = new ClientHandler(serverSocket.accept());
                clientHandler.addObserver(clientObserver);
                clientHandler.setProcessingVariables(optionsFilePath, externalPrgrmPath, brokerAddress, brokerPort
                        ,calibrationFilePath);
                
                System.out.println("Server: A Client has connected.");
                this.notifyObservers("Server: A Client has connected.");
            }
            catch(Exception ex)
            {
                this.notifyObservers("Server: Closing socket connection...");
                this.notifyObservers("Server: Connection closed.");
                ex.printStackTrace();
            }
        }
        
    }

    /**
     * Stops the clienthandler and closes the server socket.
     */
    public void stop()
    {
        try
        {
            setContinueRunning(false); //cancel while loop
            
            if(clientHandler != null)
            {
                clientHandler.stop();
            }
            
            if(serverSocket != null && serverSocket.isBound())
            {
                serverSocket.close();
            }
        
            setServerState(ServerState.SERVER_STATE_STOP); //Set server state
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("Found an error in closing server socket...");
        }
    }
    
    //**** GETTERS AND SETTERS ****//

    public int getPortNr() {
        return PORTNR;
    }

    /**
     * A public method accessable to the main class that runs teh server to sets vital variable used 
     * for execution.
     * @param serverPort server port number
     * @param brokerAddress the MQTT broker address
     * @param brokerPort the port number used by the MQTT broker
     * @param optionsFilePath the options file path
     * @param externalPrgrmPath the external executable file path(a .exe)
     * @param calibrationFilePath the file path of the calibration summary file
     * @param clientObserver an observer of the client handler. Inforamation received from a client is passed to the controller class.
     */
    public void setStartUpVaribles(int serverPort, 
            String brokerAddress, int brokerPort, String optionsFilePath, 
            String externalPrgrmPath,String calibrationFilePath,Observer clientObserver)
    {
        PORTNR = serverPort;
        this.brokerAddress = brokerAddress;
        this.brokerPort = brokerPort;
        this.optionsFilePath = optionsFilePath;
        this.externalPrgrmPath = externalPrgrmPath;
        this.calibrationFilePath = calibrationFilePath;
        this.clientObserver = clientObserver;
    }

    public boolean isContinueRunning() {
        return continueRunning;
    }

    public void setContinueRunning(boolean continueRunning) {
        this.continueRunning = continueRunning;
        this.notifyObservers();
    }
    
    //*** RUNNABLE INTERFACE ***//
    
    /**
     * Run method implemented from the Runnable interface
     */
    @Override
    public void run() 
    {
        start(); //start the server, continously listens and accepts client connection
    }
    
    //*** SERVER STATE ENUM ***//
    
    /**
     * A private Enum use to determine the server state
     */
    public enum ServerState
    {
        SERVER_STATE_READY(1000),
        SERVER_STATE_RUNNING(1001),
        SERVER_STATE_ACCEPTING(1002),
        SERVER_STATE_STOP(1003);
        
        private int code;
        
        private ServerState(int code)
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
