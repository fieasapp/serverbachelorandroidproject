package com.androidvizlab.bachelor.Sockets;

import com.androidvizlab.bachelor.Enums.SocketComMessage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

import com.androidvizlab.bachelor.datamodels.VizlabInputData;
import com.androidvizlab.bachelor.datamodels.VizlabOutputData;
import java.io.IOException;


public class ActivitySocketClient implements Runnable{
	
    //CLIENT SOCKET & PORTNR
    private Socket clientSocket = null; //socket use to connect to server
    private int PORTNR = 1330; //default
    
    //IP ADDRESS
    private String SERVER_IP = "158.38.185.49"; //server IP address
    private InetAddress serverAddress = null; //server name
    private boolean isConnected = false;
    private boolean isWaitingForResponse = false;
    
    //IN/OUTPUT STREAMS
    private ObjectOutputStream outputstream = null; //Object outputstream
    private ObjectInputStream inputstream = null; //Object inputstream
    
    //custom serialised objects
    private VizlabInputData data = null;
    private VizlabOutputData recievedData =  null;
    
    //CLIENT-SERVER MESSAGES & RESPONSES
    public static final String[] MODES = {"FROM_ACTIVITY","FROM_SERVICE"};
    
    public static final String[] SERVER_RESPONSES = {"PROCESS_STARTED",
    		"PROCESS_DONE","INPROGRESS","",""};
    
    //public static final String[] CLIENT_QUERY = {"AVAILABLE_DATA","FETCH_DATA",
    //		"GET_OPTIONSFILE","GET_RESULT_DATA","ABORT"};
    
    //NUMBER OF ATTEMPTS TO CONNECT
    private static final int MAX_ATTEMPTS = 5;
    private static final int WAIT_FOR = 2000; // milliseconds
    private Random random = new Random();
    
    //DETERMINES if the client is use for main Activity or service
    // MODES: 0 is for activity & 1 is for service use
    private int mode = -1;
    
    private String msg = "";
      
	public ActivitySocketClient()
    {
        
    }
	
	/*
	 * Initialise connection to
	 * the server via socket
	 */
    public void initConnection()
    {
    	long waitfor = WAIT_FOR + random.nextInt(2000);
        
        for(int i = 0;i <= MAX_ATTEMPTS; i++)
        {
	        try
	        {
	        	serverAddress = InetAddress.getByName(SERVER_IP);
	        	
	        	//
	        	msg = "Client: attempting to connect to server with hostname = "
	                    + serverAddress.getHostName() + ", at port = " + PORTNR;
	            
	            msg = "ATTEMPT # " + i + " to connect..";
	         
	            
	        	clientSocket = new Socket(serverAddress,PORTNR);
	        
	            if(clientSocket.isConnected())
	            {
	            	this.setConnected(true);
	            	
	            	//
		            msg = "Client is now connected to host: " + serverAddress.getHostName();
		        
		            
		            //Instantiate the input stream
		            outputstream = new ObjectOutputStream(clientSocket.getOutputStream());
		            
		            //Instantiate the output stream
		            inputstream = new ObjectInputStream(clientSocket.getInputStream());
	            }
	            else
	            {
	            	this.setConnected(false);
	            }
	            
	            return; //escape the loop when successful
	        }
	        catch(IOException ex)
	        {
	        	this.setConnected(false);
	        	
	        	msg = "Failed to connect. Attempt " + i + " : " + ex;
	        	
	        	
	            ex.printStackTrace();
	            
	            if(i == MAX_ATTEMPTS)
	            {
	            	break;
	            }
	            
	            try
	            {
	            	msg = "Sleeping for " + waitfor + "ms before retry.";
	            	
	            	Thread.sleep(waitfor);
	            }
	            catch(InterruptedException e)
	            {
	            	msg = "Aborting connection retries, thread interrupted";
	            	
	            	
	            	close();
	            	
	            	return;
	            }
	            
	            waitfor *= 2; //increase wait time before retrying
	        }
        }
    }
    
    /**
     * This method sends a serialised object
     * which contains input data from the user.
     * 
     * @param data contains options/settings that is use in the calibration/production
     */
    public void sendData(Object data)
    {
    	msg = "Ready to send data...";
    	
    	System.out.println("Client "+msg);
    	
    	try
    	{	
    		outputstream.writeObject(data);
    		
    		outputstream.flush();
    		
    		msg = "Data sent.";
    	}
    	catch(IOException ex)
    	{
    		msg = "ERROR!! " +ex;
    		ex.printStackTrace();
    		System.out.println(msg);
    	}
    	catch(Exception e)
    	{
    		msg = "ERROR!! " +e;
    		System.out.println("ERROR!! " +e);
    	}
    }
    
    /*
     * Helper method: frees used resources
     */
    public void close()
    {
    	try{
    		clientSocket.close();
    		isConnected = false;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
   
    //*** RUN METHOD ***//
    
    @Override
    public void run()
    {
	boolean continueReading = true;
    	Object obj = null;
    	
    	while(continueReading)
	{
    	
            try
            {	
                obj = inputstream.readObject();

                /*
                 * Filter out what type of object was received and
                 * execute the appropriate operation.
                 */

                if(obj instanceof String)
                {
                   String msg = (String)obj;
                }
            }
            catch(IOException e)
            {
                    continueReading = false;
                    //todo msg user
            }
            catch(Exception e)
            {
                    continueReading = false;
                    //todo msg user
            }
	}	
    }
    
	//Getter and Setters
	
	public int getPortNr() {
		return PORTNR;
	}

	public void setPortNr(int portnr) {
		PORTNR = portnr;
	}

	public String getServerIP() {
		return SERVER_IP;
	}	

	public void setServerIP(String serverip) {
		SERVER_IP = serverip;
	}
	
	public boolean isConnected() {
		
            if(clientSocket == null)
            {
                    return false;
            }
            else
            {
                    return isConnected;
            }
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	public boolean isWaitingForResponse() {
		return isWaitingForResponse;
	}

	public void setWaitingForResponse(boolean isWaitingForResponse) {
		this.isWaitingForResponse = isWaitingForResponse;
	}
	
	//*** FOR TESTING PURPOSE ONLY ***//
	public static void main(String args[])
	{
		VizlabInputData input = new VizlabInputData();
		ActivitySocketClient client = new ActivitySocketClient();
		client.initConnection();
                SocketComMessage msg = new SocketComMessage();
                msg.setMessage(SocketComMessage.SocketMessage.GET_OPTIONSFILE);
		client.sendData(msg);
	}
}