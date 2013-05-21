package com.androidvizlab.bachelor.datamodels;

import com.androidvizlab.bachelor.FileWriterAndReader.FileAccessUtility;
import com.androidvizlab.bachelor.Interface.SimpleObservable;
import com.androidvizlab.bachelor.utilities.NumberConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author The Hive
 */
public class ServerSettingsModel extends SimpleObservable{
    
    //SOCKET CONNECTION VARIABLES
    private String serverName = "localhost"; //default
    private String serverAddress = "localhost"; //default
    private int serverPort = 1330; //default
    private String brokerAddress = "localhost"; //default
    private int brokerPort = 1883; //default
    
    private boolean useLocalBrokerAddress = true; //default
    private boolean useLocalMachinename = true; //default
    
    //FILE PATH AND DIRECTORIES
    private String optionsFilePath = "";
    private String calibrationFilePath = "";
    private String externalProgramPath = "";
    
    //MAP KEYS
    public static final String KEY_SERVER_NAME = "server_name";
    public static final String KEY_SERVER_ADDRESS = "server_address";
    public static final String KEY_SERVER_PORT = "server_port";
    public static final String KEY_BROKER_ADDRESS = "broker_address";
    public static final String KEY_BROKER_PORT = "broker_port";
    public static final String KEY_LOCAL_BROKERADDRESS = "local_broker_address";
    public static final String KEY_LOCAL_MACHINENAME = "local_machinename";
    
    public static final String KEY_OPTIONSFILE_PATH = "options_file_path";
    public static final String KEY_CALIBRATIONFILE_PATH = "calibration_file_path";
    public static final String KEY_EXTERNALPROGRAM_PATH = "extprg_path";
    
    //UTILITY FOR READING AND WRITING ON TO A TEXT FILE
    private FileAccessUtility fau = null;
    
    //STORE INFO ON FIELDS IN THE SETTINGS FORM.
    private HashMap<String,String> infos = new HashMap<>();
    
    public ServerSettingsModel()
    {
        fau = new FileAccessUtility();
        
        loadConfigurationSettings(); //load configuration settings (connection settings and file paths)
        
        loadInfo(); //Loads the infomation on fields from property files
    }
    
    /**
     * Read server preferences and settings from file
     * and set the values to the model accordingly.
     */
    public void loadServerPreferences()
    {
        HashMap<String,String> settings = fau.readFromFileKeysAndValues();
        
        if(settings != null && !settings.isEmpty())
        {
            setServerName((String)settings.get(KEY_SERVER_NAME));
            setServerPort(NumberConverter.converToInt(settings.get(KEY_SERVER_PORT),1330));
            setBrokerAddress((String) settings.get(KEY_BROKER_ADDRESS));
            setBrokerPort(NumberConverter.converToInt(settings.get(KEY_BROKER_PORT),1883));
            setUseLocalBrokerAddress(Boolean.valueOf(settings.get(KEY_LOCAL_BROKERADDRESS)));
            setUseLocalMachinename(Boolean.valueOf(settings.get(KEY_LOCAL_MACHINENAME)));
            setOptionsFilePath((String)settings.get(KEY_OPTIONSFILE_PATH));
            setExternalProgramPath((String)settings.get(KEY_EXTERNALPROGRAM_PATH));
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Could not find saved preferences, will use default settings!");
            
            setServerName(serverName);
            setServerPort(serverPort);
            setBrokerAddress(brokerAddress);
            setBrokerPort(brokerPort);
            setUseLocalBrokerAddress(false);
            setUseLocalMachinename(false);
            setOptionsFilePath(optionsFilePath);
            setExternalProgramPath(externalProgramPath);
        }
    }
    
    /**
     * Saves the server preferences and settings by
     * writing it to a text file.
     */
    public synchronized void saveServerSettings()
    {
        fau.writeToFile(KEY_SERVER_NAME+":"+serverName,
                KEY_SERVER_PORT+":"+serverPort,
                KEY_BROKER_ADDRESS+":"+brokerAddress,
                KEY_BROKER_PORT+":"+brokerPort,
                KEY_LOCAL_BROKERADDRESS+":"+useLocalBrokerAddress,
                KEY_LOCAL_MACHINENAME+":"+useLocalMachinename,
                KEY_OPTIONSFILE_PATH+":"+optionsFilePath,
                KEY_EXTERNALPROGRAM_PATH+":"+externalProgramPath);
    }
    
    //*** LOAD PROPERTIES AND RESOURCE BUNDLES ***//
    
    public void loadInfo()
    {
        ResourceBundle bundle = ResourceBundle.getBundle("resources.others.FormFieldInfos");
        Enumeration keyList = bundle.getKeys();
        
        while(keyList.hasMoreElements())
        {
            String key = (String)keyList.nextElement();
            infos.put(key,bundle.getString(key));
        }
    }
    
    public String getInfo(String key)
    {
        if(infos.containsKey(key))
        {
            return (String) infos.get(key);
        }
        else
        {
            return "NO AVAILABLE INFO";
        }
    }
    
    //LOAD CONFIGURATION SETTINGS
    public void loadConfigurationSettings()
    {
        Properties config = new Properties();
        
        try 
        {
            config.load(new FileInputStream("src//resources//others//serverconfig.properties"));
            //config.load(new FileInputStream("./serverpreferences/serverconfig.properties"));
            
            setServerName((String)config.getProperty(KEY_SERVER_NAME));
            setServerPort(NumberConverter.converToInt(config.getProperty(KEY_SERVER_PORT),1330));
            setBrokerAddress((String) config.getProperty(KEY_BROKER_ADDRESS));
            setBrokerPort(NumberConverter.converToInt(config.getProperty(KEY_BROKER_PORT),1883));
            setUseLocalBrokerAddress(Boolean.valueOf(config.getProperty(KEY_LOCAL_BROKERADDRESS)));
            setUseLocalMachinename(Boolean.valueOf(config.getProperty(KEY_LOCAL_MACHINENAME)));
            setOptionsFilePath((String)config.getProperty(KEY_OPTIONSFILE_PATH));
            setExternalProgramPath((String)config.getProperty(KEY_EXTERNALPROGRAM_PATH));
        } 
        catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(null, "Could not find saved preferences, will use default settings!");
            
            setServerName(serverName);
            setServerPort(serverPort);
            setBrokerAddress(brokerAddress);
            setBrokerPort(brokerPort);
            setUseLocalBrokerAddress(false);
            setUseLocalMachinename(false);
            setOptionsFilePath(optionsFilePath);
            setExternalProgramPath(externalProgramPath);
            
            
            ex.printStackTrace();
        }
    }
    
    public void saveConfigurationSettings()
    {
        Properties config = new Properties();
        
        try 
        {
            //set property values
            
            config.setProperty(KEY_SERVER_NAME, serverName);
            config.setProperty(KEY_SERVER_PORT, serverPort+"");
            config.setProperty(KEY_BROKER_ADDRESS, brokerAddress);
            config.setProperty(KEY_BROKER_PORT, brokerPort+"");
            config.setProperty(KEY_LOCAL_MACHINENAME, useLocalMachinename+"");
            config.setProperty(KEY_LOCAL_BROKERADDRESS, useLocalBrokerAddress+"");
            
            config.setProperty(KEY_OPTIONSFILE_PATH, optionsFilePath);
            config.setProperty(KEY_EXTERNALPROGRAM_PATH, externalProgramPath);
            
            config.store(new FileOutputStream("src//resources//others//serverconfig.properties"),null);
            //config.store(new FileOutputStream("./serverpreferences/serverconfig.properties"),null);
            
            /*File dir = new File("./serverpreferences");
            
            if(!dir.exists())
            {
                if(dir.mkdir())
                {
                    config.store(new FileOutputStream("./serverpreferences/serverconfig.properties"),null);
                }
                else
                {
                    config.store(new FileOutputStream("./serverconfig.properties"),null);
                }
            }
            else
            {
                config.store(new FileOutputStream("./serverconfig.properties"),null);
            }*/
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
    }
    
        
    //*** GETTERS AND SETTERS ***//

    public String getServerName()
    {
        return serverName;
    }
    
    public void setServerName(String name)
    {
        serverName = name;
        
        this.notifyObservers();
    }
    
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

    public String getOptionsFilePath() {
        return optionsFilePath;
    }

    public void setOptionsFilePath(String optionsFilePath) {
        this.optionsFilePath = optionsFilePath;
        this.notifyObservers();
    }

    public String getCalibrationFilePath() {
        return calibrationFilePath;
    }

    public void setCalibrationFilePath(String calibrationFilePath) {
        this.calibrationFilePath = calibrationFilePath;
        this.notifyObservers();
    }

    public String getExternalProgramPath() {
        return externalProgramPath;
    }

    public void setExternalProgramPath(String externalProgramPath) {
        this.externalProgramPath = externalProgramPath;
        this.notifyObservers();
    }
}
