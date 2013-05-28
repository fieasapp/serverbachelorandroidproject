package com.androidvizlab.bachelor.Controller;

import com.androidvizlab.bachelor.Gui.ServerMainGui;
import com.androidvizlab.bachelor.Gui.SettingsDialog;
import com.androidvizlab.bachelor.Interface.DataChangeEvent;
import com.androidvizlab.bachelor.Interface.Observer;
import com.androidvizlab.bachelor.Sockets.ActivityServer;
import com.androidvizlab.bachelor.datamodels.ServerSettingsModel;
import com.androidvizlab.bachelor.utilities.NumberConverter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * This is the main Controller for the application.
 * Handles all clicks and events.
 * 
 * @author The Hive
 */
public class ServerController extends ComponentAdapter implements ActionListener,
Observer, ItemListener, HyperlinkListener{
    
    private ServerMainGui mainGui = null; //Main View
    private SettingsDialog dialog = null; //Additional View
    private ServerSettingsModel settingsModel = null; //Model
    
    //RUNNABLE AND THREADS
    private ActivityServer activityServer = null;
    
    private Thread thread = null; //Thread use to execute a runnable(ActivityServer)
    
    
    //ACTION COMMAND
    public static final String COMMAND_START = "START";
    public static final String COMMAND_STOP = "STOP";
    public static final String COMMAND_CLEAR = "CLEAR";
    public static final String COMMAND_SAVE = "SAVE";
    public static final String COMMAND_EXIT = "EXIT";
    public static final String COMMAND_SERVER_SETTINGS = "SERVERSETTINGS";
    public static final String COMMAND_HELP = "HELPCONTENT";
    public static final String COMMAND_ABOUT = "ABOUT";
    public static final String COMMAND_SAVE_FILEPATHS = "SAVEFILEPATH";
    public static final String COMMAND_CLEAR_PATHS = "CLEARFILEPATH";
    public static final String COMMAND_CHOOSE_OPT_FILEPATH = "cOptFilePath";
    public static final String COMMAND_CHOOSE_CAL_FILEPATH = "cCalFilePath";
    public static final String COMMAND_CHOOSE_EXTPRG_PATH = "cExtPrgPath";
    public static final String COMMAND_INFO_SERVERNAME = "infoServerName";
    public static final String COMMAND_INFO_SERVERPORT = "infoServerPort";
    public static final String COMMAND_INFO_MQTTBROKER = "infoMqttBrokerAddress";
    public static final String COMMAND_INFO_BROKERPORT = "infoBrokerPort";
    public static final String COMMAND_INFO_OPTNFILEPATH = "infoOptnsFilePath";
    public static final String COMMAND_INFO_EXTPRGMPATH = "infoExtPrgmPath";
    
    public ServerController()
    {
        
    }
    
    public ServerController(ServerMainGui mainGui, 
            ServerSettingsModel settingsModel, ActivityServer activityServer)
    {
        this.settingsModel = settingsModel;
        this.mainGui = mainGui;
        this.activityServer = activityServer;
    }
    
    /**
     * Set initial value of the main gui text fields
     */
    public void setMainGuiInitialValues()
    {
        //settingsModel.loadServerPreferences();
        updateMainGui();
    }
    
    /*
     * Set initial value of the settings dialog form
     */
    public void setSettingsDialogInitialValues()
    {
        mainGui.getSettingsForm().getcbUseLocalIP().setSelected(settingsModel.useLocalBrokerAddress());
        mainGui.getSettingsForm().getcbUseMachineName().setSelected(settingsModel.useLocalMachinename());
    }
    
    /**
     * Updates the main GUI 
     */
    public void updateMainGui()
    {
        mainGui.setServerNameText(settingsModel.getServerName());
        mainGui.setServerPortText(Integer.toString(settingsModel.getServerPort()));
        mainGui.setBrokerAddressText(settingsModel.getBrokerAddress());
        mainGui.setBrokerPortText(Integer.toString(settingsModel.getBrokerPort()));
        mainGui.setServerStatus(activityServer.getServerState().getValue(),activityServer.getServerState().getCode());
    }
    
    /**
     * Validates data before it is save to a text file.
     * if the data is not valid a error indicator is set to appropriate field.
     * @return returns true if the data is valid, false otherwise.
     */
    public boolean isValidSettingsData()
    {
        if(mainGui.getSettingsForm().getServerNameText() == null ||
                mainGui.getSettingsForm().getServerNameText().isEmpty())
        {
            mainGui.getSettingsForm().setError(1);
            return false;
        }
        else
        {
            mainGui.getSettingsForm().setError(-1);
        }
        
        if(mainGui.getSettingsForm().getServerPortText() == null
                || mainGui.getSettingsForm().getServerPortText().isEmpty())
        {
            mainGui.getSettingsForm().setError(2);
            return false;
        }
        else
        {
            try
            {
                Integer.parseInt(mainGui.getSettingsForm().getServerPortText());
            }catch(Exception e)
            {
                return false;
            }
            mainGui.getSettingsForm().setError(-2);
        }
        
        if(mainGui.getSettingsForm().getBrokerAddressText() == null
                || mainGui.getSettingsForm().getBrokerAddressText().isEmpty())
        {
            mainGui.getSettingsForm().setError(3);
            return false;
        }
        else
        {
            mainGui.getSettingsForm().setError(-3);
        }
        
        if(mainGui.getSettingsForm().getBrokerPortText() == null
                || mainGui.getSettingsForm().getBrokerPortText().isEmpty())
        {
            mainGui.getSettingsForm().setError(4);
            return false;
        }
        else
        {
            try
            {
                Integer.parseInt(mainGui.getSettingsForm().getBrokerPortText());
            }catch(Exception e)
            {
                return false;
            }
            mainGui.getSettingsForm().setError(-4);
        }
        
        if(mainGui.getSettingsForm().getOptionFilePath() == null 
                || mainGui.getSettingsForm().getOptionFilePath().equals(""))
        {
            mainGui.getSettingsForm().setError(5);
            return false;
        }
        else
        {
            mainGui.getSettingsForm().setError(-5);
        }
        
        if(mainGui.getSettingsForm().getExternalProgramPath() == null 
                || mainGui.getSettingsForm().getExternalProgramPath().equals(""))
        {
            mainGui.getSettingsForm().setError(6);
            return false;
        }
        else
        {
            mainGui.getSettingsForm().setError(-6);
        }
        
        if(mainGui.getSettingsForm().getCalibrationFilePath() == null 
                || mainGui.getSettingsForm().getCalibrationFilePath().equals(""))
        {
            mainGui.getSettingsForm().setError(7);
            return false;
        }
        else
        {
            mainGui.getSettingsForm().setError(-7);
        }
        
        return true;
    }
    
    /**
     * Method that execute save function after 
     * data is validated. Data is save in serverconfig.properties file.
     */
    public void saveServerSettings()
    {
        if(isValidSettingsData())
        {
            settingsModel.setServerName(mainGui.getSettingsForm().getServerNameText());
            settingsModel.setServerPort(NumberConverter.converToInt(
                    mainGui.getSettingsForm().getServerPortText(), 1330));
            settingsModel.setBrokerAddress(mainGui.getSettingsForm().getBrokerAddressText());
            settingsModel.setBrokerPort(NumberConverter.converToInt(
                    mainGui.getSettingsForm().getBrokerPortText(), 1883));
            settingsModel.setOptionsFilePath(mainGui.getSettingsForm().getOptionFilePath());
            settingsModel.setCalibrationFilePath(mainGui.getSettingsForm().getCalibrationFilePath());
            settingsModel.setExternalProgramPath(mainGui.getSettingsForm().getExternalProgramPath());
            //settingsModel.saveServerSettings(); 
            settingsModel.saveConfigurationSettings();
            mainGui.getSettingsForm().dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Failed to save server settings!");
        }
    }

    /**
     * Retrieves the local name of the machine the server is on.
     * @return Local Machine name
     */
    public String getMachineName()
    {
        String name = "localhost";
        try {
            name = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerMainGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }
    
    /**
     * Retrieves the local machine IP-address.
     * @return Local Machine IP-Address
     */
    public String getLocalIPAddress()
    {
        String name = "localhost";
        try {
            name = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerMainGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }
    
    /**
     * Start the socket server in a new thread
     */
    public void startServer()
    {
        mainGui.setEnableStartBtn(false);
        
        activityServer.setStartUpVaribles(settingsModel.getServerPort(),
                settingsModel.getBrokerAddress(),settingsModel.getBrokerPort(),
                settingsModel.getOptionsFilePath(), settingsModel.getExternalProgramPath()
                ,settingsModel.getCalibrationFilePath(),this);
        
        thread = new Thread(activityServer);
        
        thread.start();
        
        mainGui.setEnableStopBtn(true);
    }
    
    /**
     * Calls the stop function in ActivityServer and interrupt the thread.
     */
    public void stopServer()
    {
        try {
            mainGui.setEnableStopBtn(false);
            activityServer.stop();
            thread.join();
            thread = null;
            mainGui.clearServerMessages();
            mainGui.clearClientMessages();
            mainGui.setEnableStartBtn(true);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //*** COMPONENT LISTENER  ***//
    
    /**
     * Listens for a specific component to be visible and 
     * updates or set initial values.
     * @param ce source of the event
     */
    @Override
    public void componentShown(ComponentEvent ce) {
        super.componentShown(ce);
        
        if(ce.getComponent().equals(mainGui))
        { 
            setMainGuiInitialValues();
        }
        else if(ce.getComponent().equals(mainGui.getSettingsForm()))
        {
            setSettingsDialogInitialValues();
        }
    }
    
    //*** ITEM LISTENER INTERFACE ***//
    
    /**
     * Listens to change on an items state.
     * Mainly used in CheckBoxes.
     * 
     * @param ie event source
     */
    @Override
    public void itemStateChanged(ItemEvent ie) {
        
        if(ie.getItem().equals(mainGui.getSettingsForm().getcbUseMachineName()))
        {
            switch(ie.getStateChange()) //If the event is from Server name field checkbox
            {
                case 1:
                   mainGui.getSettingsForm().setServerNameText(getMachineName());
                   settingsModel.setUseLocalMachinename(true); //checkbox is checked
                    break;
                
                case 2:
                    settingsModel.setUseLocalMachinename(false); //checkbox is unchecked
                    break;
            }
        }
        else if(ie.getItem().equals(mainGui.getSettingsForm().getcbUseLocalIP()))
        {
            switch(ie.getStateChange()) //If the event is from broker address field checkbox
            {
                case 1:
                   mainGui.getSettingsForm().setBrokerAddressText(getLocalIPAddress());
                   settingsModel.setUseLocalBrokerAddress(true); //checkbox is checked
                   break;
                
                case 2:
                    settingsModel.setUseLocalBrokerAddress(false); //checkbox is unchecked
                    break;
            }
        }
        
    }
    
    //*** ACTION LISTENER INTERFACE ***//
    
    /**
     * Listens for a click event and executes the proper action 
     * accordingly.
     * @param ae 
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        String command = ae.getActionCommand();
        
        switch (command) 
        {
            case COMMAND_START:
                startServer(); //when the Start-button is clicked
                break;
                
            case COMMAND_STOP:
                stopServer(); //when the Stop-button is clicked
                break;
                
            case COMMAND_CLEAR:
                mainGui.getSettingsForm().clearForm(); //when the Clear-button is clicked
                break;
                
            case COMMAND_SAVE:
                saveServerSettings(); //when the Save-button is clicked
                break;
                
            case COMMAND_EXIT:
                mainGui.exit(); //when the the exit-menu item is clicked or Ctrl+x
                break;
                
            case COMMAND_SERVER_SETTINGS: //opens server settings dialog when Server Settings menu item is clicked or Ctrl+Alt+S
                mainGui.openSettingsDialog(settingsModel.getServerName(), settingsModel.getServerPort(),
                        settingsModel.getBrokerAddress(), settingsModel.getBrokerPort(),
                        settingsModel.getOptionsFilePath(),settingsModel.getExternalProgramPath(),
                        settingsModel.getCalibrationFilePath());
                break;
                
            case COMMAND_HELP: //opens the help window when Help menu item is clicked or Ctrl+H
                mainGui.openHelpWindow();
                break;
                
            case COMMAND_ABOUT: //opens the About window when About menu item is clicked or Ctrl+A
                mainGui.openAboutWindow();
                break;
                
            case COMMAND_CHOOSE_OPT_FILEPATH: //open a file chooser window for the options-file path field
                mainGui.openFileChooserWindow(command);
                break;
                
            case COMMAND_CHOOSE_CAL_FILEPATH: //opens a file chooser window for the calibration summary path field
                mainGui.openFileChooserWindow(command);
                break;
                
            case COMMAND_CHOOSE_EXTPRG_PATH: //opens a file chooser window for the executable program path field
                mainGui.openFileChooserWindow(command);
                break;
                
            case COMMAND_SAVE_FILEPATHS: //save server configuration settings when Save-button is clicked
                saveServerSettings();
                break;
                
            case COMMAND_CLEAR_PATHS: //save server configuration settings when Save-button is clicked
                mainGui.getSettingsForm().clearFormPaths();
                break;
                
            case COMMAND_INFO_SERVERNAME: //opens a help dialog for a given field
                mainGui.displayInfo(settingsModel.getInfo(command));
                break;
                
            case COMMAND_INFO_SERVERPORT: //opens a help dialog for a given field
                mainGui.displayInfo(settingsModel.getInfo(command));
                break;

            case COMMAND_INFO_MQTTBROKER: //opens a help dialog for a given field
                mainGui.displayInfo(settingsModel.getInfo(command));
                break;

            case COMMAND_INFO_BROKERPORT: //opens a help dialog for a given field
                mainGui.displayInfo(settingsModel.getInfo(command));
                break;
                
            case COMMAND_INFO_OPTNFILEPATH: //opens a help dialog for a given field
                mainGui.displayInfo(settingsModel.getInfo(command));
                break;
                
            case COMMAND_INFO_EXTPRGMPATH: //opens a help dialog for a given field
                mainGui.displayInfo(settingsModel.getInfo(command));
                break;
        }
    }
    
    //*** HYPERLINK LISTENER ***//
    
    /**
     * Implemented method from HyperlinkListener interface
     * Listens to event triggered by user when a link is click etc..
     * @param e HyperlinkEvent
     */
    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) 
    {
        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
        {
            try 
            {
                this.mainGui.getHelpWindow().viePage(e.getURL());
            }
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        }
    }

    //*** OBSERVER INTERFACE ***//
    
    /**
     * Receives update from an observable object and executes 
     * update on the view
     * @param e 
     */
    @Override
    public void update(DataChangeEvent<?> e) {
        updateMainGui(); //update the main view
    }

    /**
     * Receives update from an observable object and executes 
     * update on the view
     * @param e 
     */
    @Override
    public void update(DataChangeEvent<?> e, Object obj) {
        mainGui.setServerMessage((String)obj);
    }
    
    //*** GETTERS AND SETTERS ***//

    public ServerMainGui getMainGui() {
        return mainGui;
    }

    public void setMainGui(ServerMainGui mainGui) {
        this.mainGui = mainGui;
    }

    public SettingsDialog getDialog() {
        return dialog;
    }

    public void setDialog(SettingsDialog dialog) {
        this.dialog = dialog;
    }

    public ServerSettingsModel getSettingsModel() {
        return settingsModel;
    }

    public void setSettingsModel(ServerSettingsModel settingsModel) {
        this.settingsModel = settingsModel;
    }

    public ActivityServer getActivityServer() {
        return activityServer;
    }

    public void setActivityServer(ActivityServer activityServer) {
        this.activityServer = activityServer;
    }
}