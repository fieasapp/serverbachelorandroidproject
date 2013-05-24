package com.androidvizlab.bachelor.Gui;

import com.androidvizlab.bachelor.Controller.ServerController;
import com.androidvizlab.bachelor.Sockets.ActivityServer;
import com.androidvizlab.bachelor.datamodels.ServerSettingsModel;
import com.androidvizlab.bachelor.utilities.CustomFileFilter;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ItemListener;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author The Hive
 */
public class ServerMainGui extends javax.swing.JFrame {
    
    private String serverName = "";
    
    //Controller
    private ActionListener actionListener = null;
    private ItemListener itemListener = null;
    private ComponentListener componentListener = null;
    private HyperlinkListener linkListener = null;
    
    //Settings dialog
    private SettingsDialog form = null;
    private CustomFileFilter fileFilter = null;
    
    //Help window and help html related
    private HelpWindow helpWindow = null;
    private String defaultURLorFilePath = "";
    
    //About window
    private AboutDialog about = null;
    
    //Field information
    private FieldInfoDialog fieldInfo = null;
    
    /**
     * Creates new form ServerMainGui
     */
    public ServerMainGui(ActionListener actionListener,ItemListener itemListener,
            ComponentListener componentListener,HyperlinkListener linkListener)
    {
        setFrameIcon(); //set the icon shown on the top left side of the frame
        
        this.actionListener = actionListener;
        this.itemListener = itemListener;
        this.componentListener = componentListener;
        this.linkListener = linkListener;

        initComponents();
        setActionListener();
        this.setLocationRelativeTo(null);
    }
    
    /**
     * Set the image icon on the window/frame
     */
    public void setFrameIcon()
    {
        URL imageURL = ServerMainGui.class.getResource("/resources/images/frameicon.png");
        Image img = Toolkit.getDefaultToolkit().getImage(imageURL);
        
        if(imageURL != null)
        {
            ImageIcon icon = new ImageIcon(img);
            super.setIconImage(icon.getImage());//window icon
        }
    }
    
    //*** SET TEXT TO TEXT FIELD ***//
    
    public void setBrokerAddressText(String input)
    {
        dfBrokerAddress.setText(input);
    }
    
    public void setBrokerPortText(String input)
    {
        dfBrokerPort.setText(input);
    }
    
    public void setServerNameText(String input)
    {
        dfServerName.setText(input);
    }
    
    public void setServerPortText(String input)
    {
        dfServerPort.setText(input);
    }
    
    //*** GET VALUE FROM TEXT FIELD ***//
    
    public String getServerNameText()
    {
        return dfServerName.getText().trim();
    }
    
    public String getServerPortText()
    {
        return dfServerPort.getText().trim();
    }
    
    public String getBrokerAddressText()
    {
        return dfBrokerAddress.getText().trim();
    }
    
    public String getBrokerPortText()
    {
        return dfBrokerPort.getText().trim();
    }
    
    /**
     * Notify user on the server status in the status text field.
     * 
     * @param input server status text.
     * @param modes sets the colour of the text accordingly.
     */
    public void setServerStatus(String input,int modes)
    {
        dfStatus.setText(input);
        
        switch(modes)
        {
            case 1000:
                dfStatus.setForeground(Color.ORANGE);
                break;
            case 1001:
                dfStatus.setForeground(Color.GREEN);
                break;
            case 1002:
                dfStatus.setForeground(Color.CYAN);
                break;
            case 1003:
                dfStatus.setForeground(Color.RED);
                break;
        }
    }
    
    /**
     * Adds the message from the server to be viewed by user.
     * @param input server message.
     */
    public void setServerMessage(String input)
    {
        serverMsgList.add(input);
    }
    
    /**
     * Remove all server message on Server Messages tab
     */
    public void clearServerMessages()
    {
        serverMsgList.removeAll();
    }
    
    /**
     * Adds the message from the client to be viewed by user.
     * @param input client message.
     */
    public void setClientMessage(String input)
    {
        clientMsgList.add(input);        
    }
    
    /**
     * Remove all client message on Client Messages tab
     */
    public void clearClientMessages()
    {
        clientMsgList.removeAll();
    }
    
    /**
     * Sets action listener 
     */
    public void setActionListener()
    {
        btnStart.addActionListener(actionListener);
        btnStart.setActionCommand(ServerController.COMMAND_START);
        btnStop.addActionListener(actionListener);
        btnStop.setActionCommand(ServerController.COMMAND_STOP);
        exitFileMenuItem.addActionListener(actionListener);
        exitFileMenuItem.setActionCommand(ServerController.COMMAND_EXIT);
        serverSettingsMenuItem.addActionListener(actionListener);
        serverSettingsMenuItem.setActionCommand(ServerController.COMMAND_SERVER_SETTINGS);
        helpContentHelpMenuItem.addActionListener(actionListener);
        helpContentHelpMenuItem.setActionCommand(ServerController.COMMAND_HELP);
        aboutHelpMenuItem.addActionListener(actionListener);
        aboutHelpMenuItem.setActionCommand(ServerController.COMMAND_ABOUT);
    }
    
    public void setComponentListener(ComponentListener listener)
    {
        addComponentListener(listener);
    }
    
    /**
     * Create and open the setting form dialog
     */
    public void openSettingsDialog(String serverName, int port,
            String brokerAddress, int brokerPort,String optionsFilePath,
            String externalProgramPath,String calibrationPath)
    {
        form = new SettingsDialog(this,true,actionListener,itemListener);
        form.setLocationRelativeTo(null);
        form.setServerNameText(serverName);
        form.setServerPortText(Integer.toString(port));
        form.setBrokerAddressText(brokerAddress);
        form.setBrokerPortText(Integer.toString(brokerPort));
        form.setOptionFilePath(optionsFilePath);
        form.setExternalProgramPath(externalProgramPath);
        form.setCalibrationFilePath(calibrationPath);
        form.addComponentListener(componentListener);
        form.pack();
        form.setVisible(true);
    }
    
    /**
     * Open the help window. If you chose to use the URL you must use the
     * HelpWindow constructor with string parameter otherwise use the constructor
     * with a file as parameter.
     */
    public void openHelpWindow()
    {
        //helpWindow = new HelpWindow(defaultURLorFilePath);
        
        //File file = new File(defaultURLorFilePath);
        //helpWindow = new HelpWindow(file);
        URL url = ServerMainGui.class.getResource(defaultURLorFilePath);
        
        if(url == null)
        {
            url = ServerMainGui.class.getResource(defaultURLorFilePath);
        }
        else
        {
            try 
            {
                File dir = new File(url.toURI());
                
                if(!dir.exists())
                {
                   url = ServerMainGui.class.getResource(defaultURLorFilePath);
                }
            } 
            catch (URISyntaxException ex) 
            {
                url = ServerMainGui.class.getResource(defaultURLorFilePath);
            }
        }
         
        helpWindow = new HelpWindow(url);
        
        helpWindow.setHyperLinkListener(linkListener);
        helpWindow.setActionListeners(actionListener);
        helpWindow.setLocationRelativeTo(null);
        helpWindow.pack();
        helpWindow.setVisible(true);
    }
    
    /**
     * Open About window
     */
    public void openAboutWindow()
    {
        about = new AboutDialog(this,true);
        about.setLocationRelativeTo(null);
        about.pack();
        about.setVisible(true);
    }
    
    /**
     * Choose file path and directories
     */
    public void openFileChooserWindow(String command)
    {
        String path = "";
        
        if(command.equals("cExtPrgPath"))
        {
            fileFilter = new CustomFileFilter();
            fileFilter.setFileFormat(CustomFileFilter.FILE_EXTENSION_EXE);
            fileFilter.setDescription(CustomFileFilter.FILE_DESC_EXE);
            
            path = fileFilter.getFilePath();
            
            form.setExternalProgramPath(path);
        }
        else if(command.equals("cOptFilePath"))
        {
            fileFilter = new CustomFileFilter();
            fileFilter.setFileFormat(CustomFileFilter.FILE_EXTENSION_TEXT);
            fileFilter.setDescription(CustomFileFilter.FILE_DESC_TEXT);
            
            path = fileFilter.getFilePath();
            
            form.setOptionFilePath(path);
        }
        else if(command.equals("cCalFilePath"))
        {
            fileFilter = new CustomFileFilter();
            
            path = fileFilter.getDirectoryPath();
            
            form.setCalibrationFilePath(path);
        }
    }
    
    /**
     * Displays info about the given field
     */
    public void displayInfo(String info)
    {
        fieldInfo = new FieldInfoDialog(this,true);
        
        fieldInfo.setLocationRelativeTo(null);
        
        fieldInfo.displayInfo(info);
        
        fieldInfo.setVisible(true);
    }
    
    /**
     * Exits the program
     */
    public void exit()
    {
        System.exit(0);
    }
    
    //*** ENABLE/DISABLE BUTTONS ***//
    
    public void setEnableStartBtn(boolean enable)
    {
        btnStart.setEnabled(enable);
    }
    
    public void setEnableStopBtn(boolean enable)
    {
        btnStop.setEnabled(enable);
    }
    
    //*** GETTERS AND SETTERS ***//

    public SettingsDialog getSettingsForm() {
        return form;
    }

    public void setSettingsForm(SettingsDialog form) {
        this.form = form;
    }

    public String getDefaultURLOrFilePath() {
        return defaultURLorFilePath;
    }

    /**
     * Set a given URL or a File path for the help pages. Warning you must use the
     * right constructor for the HelpWindow class depending on which what parameter
     * is given to this method.
     * 
     * @param defaultURLorFilePath use a URL or a file path for the help pages. 
     */
    public void setDefaultURLOrFilePath(String defaultURLorFilePath) {
        this.defaultURLorFilePath = defaultURLorFilePath;
    }

    public HelpWindow getHelpWindow() {
        return helpWindow;
    }

    public void setHelpWindow(HelpWindow helpWindow) {
        this.helpWindow = helpWindow;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        dfServerName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dfServerPort = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        dfStatus = new javax.swing.JTextField();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        dfBrokerAddress = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        dfBrokerPort = new javax.swing.JTextField();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        serverMsgList = new java.awt.List();
        clientMsgList = new java.awt.List();
        mainMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitFileMenuItem = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        serverSettingsMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpContentHelpMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        aboutHelpMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("resources/others/LabelsAndButtonTextBundle"); // NOI18N
        setTitle(bundle.getString("mainWindowTitle")); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText(bundle.getString("labelServerName")); // NOI18N

        dfServerName.setEditable(false);
        dfServerName.setFocusable(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText(bundle.getString("labelPort")); // NOI18N

        dfServerPort.setEditable(false);
        dfServerPort.setFocusable(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText(bundle.getString("labelStatus")); // NOI18N

        dfStatus.setEditable(false);
        dfStatus.setFocusable(false);

        btnStart.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnStart.setText(bundle.getString("btnTextStart")); // NOI18N
        btnStart.setMaximumSize(new java.awt.Dimension(70, 25));
        btnStart.setMinimumSize(new java.awt.Dimension(70, 25));
        btnStart.setPreferredSize(new java.awt.Dimension(70, 25));

        btnStop.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnStop.setText(bundle.getString("btnTextStop")); // NOI18N
        btnStop.setEnabled(false);
        btnStop.setMaximumSize(new java.awt.Dimension(70, 25));
        btnStop.setMinimumSize(new java.awt.Dimension(70, 25));
        btnStop.setPreferredSize(new java.awt.Dimension(70, 25));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText(bundle.getString("labelBrokerAddress")); // NOI18N

        dfBrokerAddress.setEditable(false);
        dfBrokerAddress.setFocusable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText(bundle.getString("labelPort")); // NOI18N

        dfBrokerPort.setEditable(false);
        dfBrokerPort.setFocusable(false);

        jTabbedPane4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane4.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane4.addTab(bundle.getString("tabBtnTextServerMsg"), serverMsgList); // NOI18N
        jTabbedPane4.addTab(bundle.getString("tabBtnTextClientMsg"), clientMsgList); // NOI18N

        fileMenu.setText(bundle.getString("menuTextFile")); // NOI18N

        exitFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitFileMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/filler.png"))); // NOI18N
        exitFileMenuItem.setText(bundle.getString("menuItemTextExit")); // NOI18N
        fileMenu.add(exitFileMenuItem);

        mainMenuBar.add(fileMenu);

        settingsMenu.setText(bundle.getString("menuTextSettings")); // NOI18N

        serverSettingsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        serverSettingsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/filler.png"))); // NOI18N
        serverSettingsMenuItem.setText(bundle.getString("menuItemTextServerSettings")); // NOI18N
        settingsMenu.add(serverSettingsMenuItem);

        mainMenuBar.add(settingsMenu);

        helpMenu.setText(bundle.getString("menuTextHelp")); // NOI18N

        helpContentHelpMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        helpContentHelpMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/filler.png"))); // NOI18N
        helpContentHelpMenuItem.setText(bundle.getString("menuItemTextHelpContents")); // NOI18N
        helpMenu.add(helpContentHelpMenuItem);
        helpMenu.add(jSeparator1);

        aboutHelpMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        aboutHelpMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/filler.png"))); // NOI18N
        aboutHelpMenuItem.setText(bundle.getString("menuItemTextAbout")); // NOI18N
        helpMenu.add(aboutHelpMenuItem);

        mainMenuBar.add(helpMenu);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 933, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dfServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dfServerName, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(90, 90, 90)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dfStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dfBrokerAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                            .addComponent(dfBrokerPort))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dfServerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(dfStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(dfBrokerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dfServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(dfBrokerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerMainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerMainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerMainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerMainGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //**** MVC ***//
         
        //** Controller (Observer,Listener)**//
        ServerController controller = new ServerController();
        
        //** Main View **//
        final ServerMainGui gui = new ServerMainGui(controller,controller,
                controller,controller);
        gui.setComponentListener(controller); //Set Listener
        gui.setDefaultURLOrFilePath("/resources/helppages/english/index.html"); //default URL for the help pages(local html files)
        
        //** Model (Observable)**//
        ServerSettingsModel model = new ServerSettingsModel();
        model.addObserver(controller); //add/registre an observer
        
        //** Model (Observable)**//
        ActivityServer activityServer = new ActivityServer();
        activityServer.addObserver(controller); //add/registre an observer
        
        controller.setMainGui(gui); //add a reference of the View
        controller.setSettingsModel(model); //add a reference of a model
        controller.setActivityServer(activityServer); //add a reference of a model
        
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                gui.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutHelpMenuItem;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private java.awt.List clientMsgList;
    private javax.swing.JTextField dfBrokerAddress;
    private javax.swing.JTextField dfBrokerPort;
    private javax.swing.JTextField dfServerName;
    private javax.swing.JTextField dfServerPort;
    private javax.swing.JTextField dfStatus;
    private javax.swing.JMenuItem exitFileMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem helpContentHelpMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JMenuBar mainMenuBar;
    private java.awt.List serverMsgList;
    private javax.swing.JMenuItem serverSettingsMenuItem;
    private javax.swing.JMenu settingsMenu;
    // End of variables declaration//GEN-END:variables
}
