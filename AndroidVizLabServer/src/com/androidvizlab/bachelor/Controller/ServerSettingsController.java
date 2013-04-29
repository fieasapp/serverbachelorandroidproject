/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author The Hive
 */
public class ServerSettingsController extends ComponentAdapter implements ActionListener,
Observer, ItemListener{
    
    private ServerMainGui mainGui = null;
    private SettingsDialog dialog = null;
    private ServerSettingsModel settingsModel = null;
    
    //RUNNABLE AND THREADS
    private ActivityServer activityServer = null;
    
    private Thread thread = null;
    
    
    //ACTION COMMAND
    private final String COMMAND_START = "START";
    private final String COMMAND_STOP = "STOP";
    private final String COMMAND_CLEAR = "Clear";
    private final String COMMAND_SAVE = "Save";
    private final String COMMAND_EXIT = "Exit";
    private final String COMMAND_SERVER_SETTINGS = "Server settings";
    
    public ServerSettingsController()
    {
        
    }
    
    public ServerSettingsController(ServerMainGui mainGui, 
            ServerSettingsModel settingsModel, ActivityServer activityServer)
    {
        this.settingsModel = settingsModel;
        this.mainGui = mainGui;
        this.activityServer = activityServer;
    }
    
    /**
     * Updates the main gui 
     */
    public void updateMainGui()
    {
        mainGui.setServerNameText(settingsModel.getServerAddress());
        mainGui.setServerPortText(Integer.toString(settingsModel.getServerPort()));
        mainGui.setBrokerAddressText(settingsModel.getBrokerAddress());
        mainGui.setBrokerPortText(Integer.toString(settingsModel.getBrokerPort()));
    }
    
    /**
     * Validates data before it is save to a text file.
     * if the data is not valid a error indicator is set to appropriate field.
     * @return returns true if the data is valid, false otherwise.
     */
    public boolean isValidSettingsData()
    {
        if(mainGui.getSettingsForm().getServerAddressText() == null ||
                mainGui.getSettingsForm().getServerAddressText().isEmpty())
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
            mainGui.getSettingsForm().setError(-4);
        }
        
        return true;
    }
    
    /**
     * Method that execute save function after 
     * data is validated.
     */
    public void saveServerSettings()
    {
        if(isValidSettingsData())
        {
            settingsModel.setServerAddress(mainGui.getSettingsForm().getServerAddressText());
            settingsModel.setServerPort(NumberConverter.converToInt(
                    mainGui.getSettingsForm().getServerPortText(), 1330));
            settingsModel.setBrokerAddress(mainGui.getSettingsForm().getBrokerAddressText());
            settingsModel.setBrokerPort(NumberConverter.converToInt(
                    mainGui.getSettingsForm().getBrokerPortText(), 1330));
            settingsModel.saveServerSettings(); 
            mainGui.getSettingsForm().dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Failed to save server settings!");
        }
    }

    /**
     * Set initial value of the main gui text fields
     */
    public void setMainGuiInitialValues()
    {
        settingsModel.loadServerPreferences();
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
    
    //*** COMPONENT LISTENER  ***//
    
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
    
    @Override
    public void itemStateChanged(ItemEvent ie) {
        
        if(ie.getItem().equals(mainGui.getSettingsForm().getcbUseMachineName()))
        {
            switch(ie.getStateChange())
            {
                case 1:
                   mainGui.getSettingsForm().setServerNameText(getMachineName());
                   settingsModel.setUseLocalMachinename(true);
                    break;
                
                case 2:
                    settingsModel.setUseLocalMachinename(false);
                    break;
            }
        }
        else if(ie.getItem().equals(mainGui.getSettingsForm().getcbUseLocalIP()))
        {
            switch(ie.getStateChange())
            {
                case 1:
                   mainGui.getSettingsForm().setBrokerAddressText(getLocalIPAddress());
                   settingsModel.setUseLocalBrokerAddress(true); 
                   break;
                
                case 2:
                    settingsModel.setUseLocalBrokerAddress(false);
                    break;
            }
        }
        
    }
    
    //*** ACTION LISTENER INTERFACE ***//
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        String command = ae.getActionCommand();
        
        if(command.equals(COMMAND_START))
        {
            mainGui.setEnableStartBtn(false);
            
            thread = new Thread(activityServer);
            thread.start();
            
            mainGui.setServerStatus(ActivityServer.SERVER_STATE_RUNNING,1);
            
            mainGui.setEnableStopBtn(true);
        }
        else if(command.equals(COMMAND_STOP))
        {
            mainGui.setEnableStopBtn(false);
            
            activityServer.setContinueRunning(false);
            activityServer.stop();
            thread.interrupt();
            
            mainGui.setServerStatus(ActivityServer.SERVER_STATE_STOP, 2);
            
            mainGui.setEnableStartBtn(true);
        }
        else if(command.equals(COMMAND_CLEAR))
        {
            mainGui.getSettingsForm().clearForm();
        }
        else if(command.equals(COMMAND_SAVE))
        {
            saveServerSettings();
        }
        else if(command.equals(COMMAND_EXIT))
        {
            mainGui.exit();
        }
        else if(command.equals(COMMAND_SERVER_SETTINGS))
        {
            mainGui.openSettingsDialog(settingsModel.getServerAddress(), settingsModel.getServerPort(),
                    settingsModel.getBrokerAddress(), settingsModel.getBrokerPort());
        }
    }

    //*** OBSERVER INTERFACE ***//
    
    @Override
    public void update(DataChangeEvent<?> e) {
        System.out.println("UPDATED");
        updateMainGui();
    }

    @Override
    public void update(DataChangeEvent<?> e, Object obj) {
        
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
