package com.androidvizlab.bachelor.Sockets;

import com.androidvizlab.bachelor.Enums.SocketMessage;
import com.androidvizlab.bachelor.FileWriterAndReader.CameraFileReader;
import com.androidvizlab.bachelor.FileWriterAndReader.OptionsFileReader;
import com.androidvizlab.bachelor.FileWriterAndReader.OptionsFileWriter;
import com.androidvizlab.bachelor.Interface.DataChangeEvent;
import com.androidvizlab.bachelor.Interface.Observer;
import com.androidvizlab.bachelor.MQTT.MessagePublisher;
import com.androidvizlab.bachelor.datamodels.VizlabInputData;
import com.androidvizlab.bachelor.datamodels.VizlabOutputData;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import runtimetest.ExternalProcessHandler;

/**
 *
 * @author The Hive
 */
public class ClientHandler implements Runnable, Observer{
    
    //CLIENTS SOCKET
    private Socket conSocket = null;
    
    //IN/OUTPUT STREAM
    private ObjectInputStream inputstream = null;
    private ObjectOutputStream outputstream = null;
    
    //THREAD
    private Thread t = null;
    
    //VizlabInputData
    private VizlabInputData data = new VizlabInputData();
    private VizlabInputData input = new VizlabInputData();
    
    //READ CONTINUOUSLY
    private boolean continueReading = true;
    
    //VizlaOutputData
    private VizlabOutputData output = null;
    
    //MQTT
    private MessagePublisher publisher = null;
    
    //AN EXTERNAL PROCESS TO BE EXECUTED
    private ExternalProcessHandler eph = null;
    
    //READERS AND WRITERS(OPTIONSFILE & CALIBRATION FILES)
    private OptionsFileWriter optionsFileWriter = null;
    private OptionsFileReader optionsFileReader = null;
    private CameraFileReader camFileReader = null;
    
    //FILE PATH AND FILENAMES
    private static final String OPTIONS_FILE_PATH = 
            "src//com//androidvizlab//bachelor//calibrationandoptionsfile//options.txt";
    
    private static final String CALIBRATION_FILE_PATH = "";
   
    /**
     * Constructor that accepts server socket connection
     * 
     * @param socketAccept socket accepted by the server
     */
    public ClientHandler(Socket socketAccept)
    {
        //FILE READERS
        camFileReader = new CameraFileReader();
        
        optionsFileReader = new OptionsFileReader(new File(OPTIONS_FILE_PATH));
        
        //SERVER SOCKET ACCEPT
        conSocket = socketAccept;
        
        //MQTT PUBLISHER
        publisher = new MessagePublisher();
        
        //EXTERNAL PROCESS TO BE EXECUTED
        eph = new ExternalProcessHandler();
        eph.addObserver(this); //Add an Observer
        
        try
        {   
            outputstream = new ObjectOutputStream(conSocket.getOutputStream());
            inputstream = new ObjectInputStream(conSocket.getInputStream());
            
            t = new Thread(this);
            t.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
 
    public void stop()
    {
        try
        {
            if(!conSocket.isClosed() && conSocket != null)
            {
                conSocket.close();
            }
            t.join();
            t = null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * To be executed when thread start() is invoked
     */
    @Override
    public void run() 
    {    
        while(continueReading)
        {
            try
            {
                if(conSocket.isClosed() || conSocket == null)
                {
                    continue;
                }
                
                Object obj = inputstream.readObject();
                
                /*
                 * Filters out what type of object was received and
                 * execute appropriate action accordingly
                 */
                
                if(obj instanceof SocketMessage) 
                {
                    SocketMessage msg = (SocketMessage) obj;
                    
                    if(msg == SocketMessage.GET_OPTIONSFILE) // Get the current options file
                    {
                        //Read optionfile, send the optionfile as VizlabInput
                     
                        data = optionsFileReader.getData();
                        
                        System.out.println("Request received, sending options.txt file...");
                        
                        sendData(data);
                        
                        System.out.println("File sent.");
                    }
                    else if(msg == SocketMessage.GET_RESULT_DATA) //Get the result of the calibration
                    {
                        //Sends the camera group and camera as Vizlab output (CameraGroup & Camera class)
                        
                        //camFileReader.realCalibrationSummaryFile(new File("src//com.androidvizlab.bachelor.calibrationandoptionsfile//calibration_summary.dat"));
                        
                        System.out.println("Fetching calibration results...");
                        
                        //READ FROM FILES
                        camFileReader.readCalibrationCombFile(new File("src//com//androidvizlab//bachelor//calibrationandoptionsfile//calibrationcob.dat"));
                        
                        camFileReader.readCalibrationCombFile(new File("src//com//androidvizlab//bachelor//calibrationandoptionsfile//calibrationcob2.dat"));
                        
                        camFileReader.readCalibrationCombFile(new File("src//com//androidvizlab//bachelor//calibrationandoptionsfile//calibrationcob3.dat"));
                        
                        //SEND as VizlabOutputData object
                        output = new VizlabOutputData();
                        
                        output.setListCamGrp(camFileReader.getCameraGroups());

                        System.out.println("Sending calibration results...");
                        
                        sendData(output);
                        
                        System.out.println("Requested data sent.");
                    }
                }
                else if(obj instanceof VizlabInputData)
                {
                    //Overwrite optionsfile.txt and execute external process/program(Vizlab)
                    
                    System.out.println("VizlabInputData received.");
                    
                    System.out.println("Overwriting optionsfile.txt . . .");
                    
                    input = (VizlabInputData) obj;
                    
                    try 
                    {
                        optionsFileWriter = new OptionsFileWriter(new File(OPTIONS_FILE_PATH),input);
                        
                        optionsFileWriter.writeToFile();
                    } 
                    catch (IOException ex)
                    {
                        System.out.println("Failed to overwrite file.");
                    
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    System.out.println("File overwritten.\nExecuting external process...");
                    
                    executeExternalProcess();       
                }
            }
            catch(Exception e)
            {
                //continueReading = false;
                e.printStackTrace();
                System.out.println("Error: " + e);
            }
        }
    }
    
    //Send data - VizlabInpuData or VizlabOutputData
    public void sendData(Object object)
    {
    	String msg = "Ready to send data...";
    	
    	System.out.println(msg);
    	
    	try
    	{
            outputstream.writeObject(object);

            outputstream.flush();
            
            msg = "Data is sent.";
    	
            System.out.println(msg);
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
    
    //Send message
    public void sendMessage(String m)
    {
    	String msg = "Ready to send message...";
    	
    	System.out.println(msg);
    	
    	try
    	{
            outputstream.writeObject(m);

            outputstream.flush();
            
            msg = "Message is sent.";
    	
            System.out.println(msg);
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
    
    /**
     * Execute the external process
     */
    private void executeExternalProcess()
    {
        eph.runProcess();
    }
    
    /**
     * Publish to a topic in the message broker. The message
     * will be sent to registered subscribers. 
     */
    public void publishToTopic()
    {
        publisher = new MessagePublisher();
        publisher.setupConnection(); //Connect to broker
        publisher.publish("new_available_data", "New data available"); //Publish on topic
        publisher.disconnect(); //disconnect when done
    }

    /**
     * Observer interface method -
     * Allows observer to perform updates when changes in the observable happened
     * 
     * @param e
     * @param obj 
     */
    @Override
    public void update(DataChangeEvent<?> e, Object obj) {
        if(obj instanceof String)
        {
            String procMsg = (String) obj;
            
            System.out.println(procMsg);
            
            if(procMsg.equalsIgnoreCase("PROCESS_STARTED"))
            {
                //sendMessage(procMsg);
                SocketMessage smsg = SocketMessage.PROCESS_STARTED;
                sendData(smsg);
            }
            else if(procMsg.equalsIgnoreCase("PROCESS_EXITED_NORMAL"))
            {
                publishToTopic();
            }
            else if(procMsg.equalsIgnoreCase("PROCESS_EXITED_WITH_ERROR"))
            {
                //TODO publish to topic "ERROR".
            }
        }
    }
    
    @Override
    public void update(DataChangeEvent<?> e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
