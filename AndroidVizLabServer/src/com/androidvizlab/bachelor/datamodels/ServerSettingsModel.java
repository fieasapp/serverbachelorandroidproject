package com.androidvizlab.bachelor.datamodels;

import com.androidvizlab.bachelor.FileWriterAndReader.folder.FileAccessUtility;
import com.androidvizlab.bachelor.Interface.SimpleObservable;
import com.androidvizlab.bachelor.utilities.NumberConverter;
import java.util.HashMap;

/**
 *
 * @author The Hive
 */
public class ServerSettingsModel extends SimpleObservable{
    
    private String serverAddress = "localhost";
    private int serverPort = 1330;
    private String brokerAddress = "localhost";
    private int brokerPort = 1883;
    
    //MAP KEYS
    public static final String KEY_SERVER_ADDRESS = "server_address";
    public static final String KEY_SERVER_PORT = "server_port";
    public static final String KEY_BROKER_ADDRESS = "broker_address";
    public static final String KEY_BROKER_PORT = "broker_port";
    
    //UTILITY FOR READING AND WRITING ON TO A TEXT FILE
    private FileAccessUtility fau = null;
    
    public ServerSettingsModel()
    {
        fau = new FileAccessUtility();
    }
    
    /**
     * Read server preferences and settings from file
     * and set the values to the model accordingly.
     */
    public void loadServerPreferences()
    {
        HashMap<String,String> settings = fau.readFromFile();
        
        if(settings != null)
        {
            setServerAddress((String)settings.get(KEY_SERVER_ADDRESS));
            setServerPort(NumberConverter.converToInt(settings.get(KEY_SERVER_PORT),1330));
            setBrokerAddress((String) settings.get(KEY_BROKER_ADDRESS));
            setBrokerPort(NumberConverter.converToInt(settings.get(KEY_BROKER_PORT),1883));
        }
    }
    
    public synchronized void saveServerSettings()
    {
        fau.writeToFile(KEY_SERVER_ADDRESS+":"+serverAddress,
                KEY_SERVER_PORT+":"+serverPort,
                KEY_BROKER_ADDRESS+":"+brokerAddress,
                KEY_BROKER_PORT+":"+brokerPort);
    }
        
    //*** GETTERS AND SETTERS ***//

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
        this.notifyObservers();
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
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
}
