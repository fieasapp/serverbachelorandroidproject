package com.androidvizlab.bachelor.Sockets;

import com.androidvizlab.bachelor.Enums.SocketMessage;
import com.androidvizlab.bachelor.FileWriterAndReader.CameraFileReader;
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
    //private VizlabOutputData output = null;
    //private CameraGroup output = null;
    
    //MQTT
    private MessagePublisher publisher = null;
    
    private static final String CLIENT_REQUEST_MSG[] = {"GET_OPTIONSFILE","GET_RESULT_DATA","ABORT"};
    
    //AN EXTERNAL PROCESS TO BE EXECUTED
    ExternalProcessHandler eph = null;
    
    //READ AND WRITE OPTIONSFILE
    private OptionsFileWriter optionsFileWriter = null;
    private CameraFileReader camFileReader = null;
   
    /**
     * Constructor that accepts server socket connection
     * 
     * @param socketAccept socket accepted by the server
     */
    public ClientHandler(Socket socketAccept)
    {
        data.setCalibrationFilePath("C://direct/");
        data.setCalibrationFileName("C://direct/ffaff.dat");
        data.setRunType("C");
        data.setTriggingInterval(200);
        data.setNumTripletCamGrp(2);
        data.setNumMarkers(5);
        data.setNumTimePts(200);
        data.setProgramOutputSocketConnection("T");
        data.setImgFileInputTriplets("T");
        data.setImgFileInputTripletsTurnedRight("T");
        data.setImgFileOutputOriginalImg("T");
        data.setImgFileOutputGeneratedTriplets("T");
        data.setImgFileOutputGeneratedTripletsTurnedRight("T");
        data.setHelpFileOutputImageDetectPoints("T");
        data.setHelpFileOutputMatch3("T");
        data.setHelpFileOutputdobbelMatch("T");
        data.setHelpFileOutputDuplicatePoints("T");
        data.setHelpFileOutputConnectPoints("T");
        data.setHelpFileOutputTimeseries("T");
        data.setApproxFrameMarkerLimit(1000.0);
        
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
                 * Filter out what type of object was received and
                 * execute appropriate action accordingly
                 */
                
                if(obj instanceof String)
                {
                    String msg = (String)obj;
                    
                    if(msg.equals(CLIENT_REQUEST_MSG[0])) // Get the current options file
                    {
                        //TODO read optionfile, send the optionfile as VizlabInput
                        //data = new VizlabInputData();
                        //sendData(data);
                        System.out.println("Request received, sending options.txt file...");
                        sendData(data);
                    }
                    else if(msg.equals(CLIENT_REQUEST_MSG[1])) //Get the result of the calibration
                    {
                        //TODO send the camera combination and camera info as Vizlab output (CameraGroup)
                        //output = new CameraGroup();
                        //sendData(output);
                        
                        camFileReader.realCalibrationSummaryFile(new File("src//com.androidvizlab.bachelor.calibrationandoptionsfile//calibration_summary.dat"));
                        
                        camFileReader.readCalibrationCombFile(new File("src//com.androidvizlab.bachelor.calibrationandoptionsfile//calibrationcob.dat"));
                        
                        camFileReader.readCalibrationCombFile(new File("src//com.androidvizlab.bachelor.calibrationandoptionsfile//calibrationcob2.dat"));
                        
                        camFileReader.readCalibrationCombFile(new File("src//com.androidvizlab.bachelor.calibrationandoptionsfile//calibrationcob3.dat"));
                        
                        VizlabOutputData output = new VizlabOutputData();
                        output.setListCamGrp(camFileReader.getCameraCombInfo());
                        output.setListCamGrp(camFileReader.getRecommendedCameraGroups());
                        sendData(output);
                    }
                }
                else if(obj instanceof VizlabInputData)
                {
                    System.out.println("VizlabInputData received.");
                    //TODO rewrite optionsfile and execute external process/program(Vizlab)
                    
                    input = (VizlabInputData) obj;
                    
                    try {
                        optionsFileWriter = new OptionsFileWriter(new File("src//options.txt"),input);
                    } catch (IOException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    optionsFileWriter.writeToFile();
                    executeExternalProcess();       
                }
                else if(obj instanceof SocketMessage)
                {
                    System.out.println("REACHED!!");
                    SocketMessage msg = (SocketMessage) obj;
                    if(msg == SocketMessage.GET_OPTIONSFILE)
                    {
                        System.out.println("DUH"+msg.toString());
                    }
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
        publisher.publish("new_data", "New data available"); //Publish on topic
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
                sendMessage(procMsg);
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
