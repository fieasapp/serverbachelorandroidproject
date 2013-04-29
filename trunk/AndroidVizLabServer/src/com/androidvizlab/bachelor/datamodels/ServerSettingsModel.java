package com.androidvizlab.bachelor.datamodels;

import com.androidvizlab.bachelor.FileWriterAndReader.FileAccessUtility;
import com.androidvizlab.bachelor.Interface.SimpleObservable;
import com.androidvizlab.bachelor.utilities.NumberConverter;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author The Hive
 */
public class ServerSettingsModel extends SimpleObservable{
    
    private String serverAddress = "localhost"; //default
    private int serverPort = 1330; //default
    private String brokerAddress = "localhost"; //default
    private int brokerPort = 1883; //default
    
    private boolean useLocalBrokerAddress = true; //default
    private boolean useLocalMachinename = true; //default
    
    //MAP KEYS
    public static final String KEY_SERVER_ADDRESS = "server_address";
    public static final String KEY_SERVER_PORT = "server_port";
    public static final String KEY_BROKER_ADDRESS = "broker_address";
    public static final String KEY_BROKER_PORT = "broker_port";
    public static final String KEY_LOCAL_BROKERADDRESS = "local_broker_address";
    public static final String KEY_LOCAL_MACHINENAME = "local_machinename";
    
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
        
        if(settings != null && !settings.isEmpty())
        {
            setServerAddress((String)settings.get(KEY_SERVER_ADDRESS));
            setServerPort(NumberConverter.converToInt(settings.get(KEY_SERVER_PORT),1330));
            setBrokerAddress((String) settings.get(KEY_BROKER_ADDRESS));
            setBrokerPort(NumberConverter.converToInt(settings.get(KEY_BROKER_PORT),1883));
            setUseLocalBrokerAddress(Boolean.valueOf(settings.get(KEY_LOCAL_BROKERADDRESS)));
            setUseLocalMachinename(Boolean.valueOf(settings.get(KEY_LOCAL_MACHINENAME)));
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Could not find saved preferences, will use default settings!");
            
            setServerAddress(serverAddress);
            setServerPort(serverPort);
            setBrokerAddress(brokerAddress);
            setBrokerPort(brokerPort);
            setUseLocalBrokerAddress(false);
            setUseLocalMachinename(false);
        }
    }
    
    /**
     * Saves the server preferences and settings by
     * writing it to a text file.
     */
    public synchronized void saveServerSettings()
    {
        fau.writeToFile(KEY_SERVER_ADDRESS+":"+serverAddress,
                KEY_SERVER_PORT+":"+serverPort,
                KEY_BROKER_ADDRESS+":"+brokerAddress,
                KEY_BROKER_PORT+":"+brokerPort,
                KEY_LOCAL_BROKERADDRESS+":"+useLocalBrokerAddress,
                KEY_LOCAL_MACHINENAME+":"+useLocalMachinename);
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

    public boolean useLocalBrokerAddress() {
        return useLocalBrokerAddress;
    }

    public void setUseLocalBrokerAddress(boolean useLocalBrokerAddress) {
        this.useLocalBrokerAddress = useLocalBrokerAddress;
        this.notifyObservers();
    }

    public boolean useLocalMachinename() {
        return useLocalMachinename;
    }

    public void setUseLocalMachinename(boolean useLocalMachinename) {
        this.useLocalMachinename = useLocalMachinename;
        this.notifyObservers();
    }
}
