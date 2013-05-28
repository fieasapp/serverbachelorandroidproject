package com.androidvizlab.bachelor.Sockets;

import com.androidvizlab.bachelor.Enums.SocketMessage;
import com.androidvizlab.bachelor.FileWriterAndReader.CameraFileReader;
import com.androidvizlab.bachelor.FileWriterAndReader.CameraFileWriter;
import com.androidvizlab.bachelor.FileWriterAndReader.FileAccessUtility;
import com.androidvizlab.bachelor.FileWriterAndReader.OptionsFileReader;
import com.androidvizlab.bachelor.FileWriterAndReader.OptionsFileWriter;
import com.androidvizlab.bachelor.Interface.DataChangeEvent;
import com.androidvizlab.bachelor.Interface.Observer;
import com.androidvizlab.bachelor.Interface.SimpleObservable;
import com.androidvizlab.bachelor.MQTT.MessagePublisher;
import com.androidvizlab.bachelor.datamodels.UniqueCamGroups;
import com.androidvizlab.bachelor.datamodels.VizlabInputData;
import com.androidvizlab.bachelor.datamodels.VizlabOutputData;
import com.androidvizlab.bachelor.utilities.CustomFileFilter;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import runtimetest.ExternalProcessHandler;

/**
 * Handles client connections in new thread.
 * @author The Hive
 */
public class ClientHandler extends SimpleObservable implements Runnable, Observer{
    
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
    private int brokerPort = 1883;
    private String brokerAddress = "localhost";
    
    //AN EXTERNAL PROCESS TO BE EXECUTED
    private ExternalProcessHandler eph = null; //Handles the execution of the external program
    
    //READERS AND WRITERS(OPTIONSFILE & CALIBRATION FILES)
    private OptionsFileWriter optionsFileWriter = null; //For overwritting options.txt file.
    private OptionsFileReader optionsFileReader = null; //For reading options.txt file.
    private CameraFileReader camFileReader = null; //For reading calibration summary file.
    private CameraFileWriter camFileWriter = null; //For overwritting calibration summary files for Production.
    
    //FILE PATH AND FILENAMES
    private String optionsFilePath = "";
    
    private String calibrationFilePath = "";
    
    private String externalPrgrmPath = ""; 
   
    /**
     * Constructor that accepts server socket connection
     * 
     * @param socketAccept socket accepted by the server
     */
    public ClientHandler(Socket socketAccept)
    {
        //FILE READERS
        camFileReader = new CameraFileReader();
        
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
 
    /**
     * Closes the socket connection and stops the client thread.
     */
    public void stop()
    {
        try
        {
            if(publisher != null)
            {
                publisher.disconnect();
            }
            
            setContinueReading(false);
            
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
                     
                        optionsFileReader = new OptionsFileReader(new File(optionsFilePath));
                        
                        data = optionsFileReader.getData();
                        
                        System.out.println("Request received, sending options.txt file...");
                        notifyObservers("Request received, sending options.txt file...");
                        
                        sendData(data);
                        
                        System.out.println("File sent.");
                        notifyObservers("File sent.");
                    }
                    else if(msg == SocketMessage.GET_RESULT_DATA) //Get the result of the calibration
                    {
                        //Sends the camera group and camera as Vizlab output (CameraGroup & Camera class)
                    
                        System.out.println("Fetching calibration results...");
                        notifyObservers("Fetching calibration results...");
                        
                        //READ FROM FILES
                                
                        FileAccessUtility fau = new FileAccessUtility();
                        
                        File summary = fau.getLatestCalibrationSummaryFile(calibrationFilePath, CustomFileFilter.FILE_EXTENSION_DAT);
                        
                        camFileReader.readCalibrationSummaryFile(summary);
                        
                        ArrayList<UniqueCamGroups> uniquegrp = camFileReader.getAllUniqueCamGrpSorted();
          
                        //SEND as VizlabOutputData object
                        output = new VizlabOutputData();
                        output.setUniqueCamGrp(uniquegrp);
                        output.setListCamGrp(camFileReader.getCameraGroups());

                        //output.setListCamGrp("");
                        
                        System.out.println("Sending calibration results...");
                        notifyObservers("Sending calibration results...");
                        
                        sendData(output);
                        
                        System.out.println("Requested data sent.");
                        notifyObservers("Requested data sent.");
                    }
                }
                else if(obj instanceof VizlabInputData)
                {
                    //Overwrite optionsfile.txt and execute external process/program(Vizlab)
                    
                    System.out.println("VizlabInputData received.");
                    notifyObservers("VizlabInputData received.");
                    
                    System.out.println("Overwriting optionsfile.txt . . .");
                    notifyObservers("Overwriting optionsfile.txt . . .");
                    
                    input = (VizlabInputData) obj;
                    
                    if(input != null)
                    {
                        try 
                        {
                            if(input.getRunType().equals("P"))
                            {
                                camFileWriter = new CameraFileWriter(new File(input.getCalibrationFilePath().trim()+input.getCalibrationFileName().trim()));
                                camFileWriter.setCameraGroups(input.getSelectedTripCamGrps());
                                camFileWriter.writeToFile();
                            }
                            
                            optionsFileWriter = new OptionsFileWriter(new File(optionsFilePath),input);

                            optionsFileWriter.writeToFile();
                        } 
                        catch (IOException ex)
                        {
                            System.out.println("Failed to overwrite file.");

                            notifyObservers("Failed to overwrite file.");
                        }

                        System.out.println("File overwritten.\nExecuting external process...");
                        notifyObservers("File overwritten.");
                        notifyObservers("Executing external process...");

                        executeExternalProcess();
                    }       
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("Error: " + e);
                continueReading = false;
            }
        }
    }
    
    //Send data - VizlabInpuData or VizlabOutputData
    /**
     * Method to send data through the socket stream.
     * @param object object that are sent must be serialized.
     */
    public void sendData(Object object)
    {
    	String msg = "Ready to send data...";
    	
    	System.out.println(msg);
    	notifyObservers(msg);
        
    	try
    	{
            outputstream.writeObject(object);

            outputstream.flush();
            
            msg = "Data is sent.";
    	
            System.out.println(msg);
            notifyObservers(msg);
    	}
    	catch(IOException ex)
    	{
            msg = "ERROR!! " +ex;
            ex.printStackTrace();
            System.out.println(msg);
            notifyObservers(msg);
    	}
    	catch(Exception e)
    	{
            msg = "ERROR!! " +e;
            System.out.println("ERROR!! " +e);
            notifyObservers(msg);
    	}
    }
    
    //Send message
    /**
     * Sends a string through the socket stream.
     * @param m a string message sent through the stream.
     */
    public void sendMessage(String m)
    {
    	String msg = "Ready to send message...";
    	
    	System.out.println(msg);
        notifyObservers(msg);
    	
    	try
    	{
            outputstream.writeObject(m);

            outputstream.flush();
            
            msg = "Message is sent.";
    	
            System.out.println(msg);
            notifyObservers(msg);
    	}
    	catch(IOException ex)
    	{
            msg = "ERROR!! " +ex;
            ex.printStackTrace();
            System.out.println(msg);
            notifyObservers(msg);
    	}
    	catch(Exception e)
    	{
            msg = "ERROR!! " +e;
            System.out.println("ERROR!! " +e);
            notifyObservers(msg);
    	}
    }
    
    /**
     * Execute the external process
     */
    private void executeExternalProcess()
    {
        eph.setExecutableFilePath(externalPrgrmPath);
        eph.runProcess();
    }
    
    /**
     * Publish to a topic in the message broker. The message
     * will be sent to registered subscribers. 
     */
    public void publishToTopic()
    {
        publisher = new MessagePublisher();
        
        publisher.setBrokerName(brokerAddress);
        publisher.setBrokerPortNr(brokerPort);
        
        publisher.setupConnection(); //Connect to broker
        
        publisher.publish(MessagePublisher.PUBLISH_TO_TOPIC, "New data available"); //Publish on topic
        
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
            notifyObservers(procMsg);
            
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
    
    /**
     * Observer interface method -
     * Allows observer to perform updates when changes in the observable happened
     * 
     * @param e
     */
    @Override
    public void update(DataChangeEvent<?> e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //*** GETTERS AND SETTERS ***//

    public boolean isContinueReading() {
        return continueReading;
    }

    public void setContinueReading(boolean continueReading) {
        this.continueReading = continueReading;
        
        notifyObservers();
    }
    
    /**
     * An method used by the server to set the proper values for paths and location of
     * certain resources need to run the appliction.
     * @param optionsFilePath File path of the options.txt file.
     * @param externalPrgrmPath File path and name of the executable file (.exe)
     * @param brokerAddress MQTT's IP address
     * @param brokerPort MQTT's port number
     * @param calibrationFilePath File path for the calibration summary.
     */
    public void setProcessingVariables(String optionsFilePath, 
            String externalPrgrmPath, String brokerAddress, int brokerPort,String calibrationFilePath)
    {
        this.optionsFilePath = optionsFilePath;
        this.externalPrgrmPath = externalPrgrmPath;
        this.brokerAddress = brokerAddress;
        this.brokerPort = brokerPort;
        this.calibrationFilePath = calibrationFilePath;
    }
}
