package com.androidvizlab.bachelor.Gui;

import com.androidvizlab.bachelor.Controller.ServerSettingsController;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

/**
 *
 * @author The Hive
 */
public class SettingsDialog extends javax.swing.JDialog {

    //Helper class to read and write to a text file
    private ActionListener actionListener = null;
    private ItemListener itemListener = null;
    
    /**
     * Creates new form SettingsDialog
     */
    public SettingsDialog(Frame parent, boolean modal, 
            ActionListener actionListener, ItemListener itemListener) {
        super(parent, modal);
        super.setIconImage(new ImageIcon("src/resources/images/frameicon.png").getImage());
        
        this.actionListener = actionListener;
        this.itemListener = itemListener;
        
        /*
         * Initialises various components
         */
        initComponents();
        setActionListener();
        setAllErrorLabelsInvisibility(); //make the error label invisible when the window loads.
    }
    
    /**
     * Clears all Text fields in the form
     */
    public void clearForm()
    {
        tfBrokerAddress.setText("");
        tfBrokerPort.setText("");
        tfServerName.setText("");
        tfServerPort.setText("");
        cbUseMachineName.setSelected(false);
        cbBrokerAddress.setSelected(false);
    }
    
    /**
     * Set action listener
     */
    public void setActionListener()
    {
        btnClear.addActionListener(actionListener);
        btnSave.addActionListener(actionListener);
        cbUseMachineName.addItemListener(itemListener);
        cbBrokerAddress.addItemListener(itemListener);
        btnChooseOptionFilePath.addActionListener(actionListener);
        btnChooseOptionFilePath.setActionCommand(ServerSettingsController.COMMAND_CHOOSE_OPT_FILEPATH);
        btnChooseCalibrationFilePath.addActionListener(actionListener);
        btnChooseCalibrationFilePath.setActionCommand(ServerSettingsController.COMMAND_CHOOSE_CAL_FILEPATH);
        btnChooseExtProgPath.addActionListener(actionListener);
        btnChooseExtProgPath.setActionCommand(ServerSettingsController.COMMAND_CHOOSE_EXTPRG_PATH);
        btnSaveFilePaths.addActionListener(actionListener);
        btnSaveFilePaths.setActionCommand(ServerSettingsController.COMMAND_SAVE_FILEPATHS);
        btnClearPaths.addActionListener(actionListener);
        btnClearPaths.setActionCommand(ServerSettingsController.COMMAND_CLEAR_PATHS);
    }
    
    //*** SET TEXT TO TEXT FIELD ***//
    
    public void setBrokerAddressText(String input)
    {
        tfBrokerAddress.setText(input);
    }
    
    public void setBrokerPortText(String input)
    {
        tfBrokerPort.setText(input);
    }
    
    public void setServerNameText(String input)
    {
        tfServerName.setText(input);
    }
    
    public void setServerPortText(String input)
    {
        tfServerPort.setText(input);
    }
    
    public void setOptionFilePath(String input)
    {
        tfOptFilePath.setText(input);
    }
    
    public void setCalibrationFilePath(String input)
    {
        tfCalFilePath.setText(input);
    }
    
    public void setExternalProgramPath(String input)
    {
        tfExtPrgPath.setText(input);
    }
    
    //*** GET TEXT FROM TEXT FIELDS ***//
    
    public String getServerAddressText()
    {
        return tfServerName.getText().trim();
    }
    
    public String getServerPortText()
    {
        return tfServerPort.getText().trim();
    }
    
    public String getBrokerAddressText()
    {
        return tfBrokerAddress.getText().trim();
    }
    
    public String getBrokerPortText()
    {
        return tfBrokerPort.getText().trim();
    }
    
    public String getOptionFilePath()
    {
        return tfOptFilePath.getText().trim();
    }
    
    public String getCalibrationFilePath()
    {
        return tfCalFilePath.getText().trim();
    }
    
    public String getExternalProgramPath()
    {
        return tfExtPrgPath.getText().trim();
    }
    
    //*** CHECKBOX ACCESS REFERENCE ***//
    public JCheckBox getcbUseMachineName()
    {
        return cbUseMachineName;
    }
    
    public JCheckBox getcbUseLocalIP()
    {
        return cbBrokerAddress;
    }
    
    //*** SET ERROR INDICATOR ***//
    
    /**
     * 
     * @param i 
     */
    public void setError(int i)
    {
        errorLabelServerSettings.setVisible(true);
        errorLabelServerSettings.setText("Field required.");
    }
    
    /**
     * Set all error labels invisible
     */
    public void setAllErrorLabelsInvisibility()
    {
        errorLabelServerSettings.setVisible(false);
        errorLabelFilePathAndDirectories.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnClear = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfServerName = new javax.swing.JTextField();
        btnInfoServerName = new javax.swing.JButton();
        cbUseMachineName = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        tfServerPort = new javax.swing.JTextField();
        btnInfoServerPort = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfBrokerAddress = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbBrokerAddress = new javax.swing.JCheckBox();
        btnInfoBrokerAddress = new javax.swing.JButton();
        tfBrokerPort = new javax.swing.JTextField();
        btnInfoBrokerPort = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        errorLabelServerSettings = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfOptFilePath = new javax.swing.JTextField();
        tfCalFilePath = new javax.swing.JTextField();
        btnChooseOptionFilePath = new javax.swing.JButton();
        btnChooseCalibrationFilePath = new javax.swing.JButton();
        btnSaveFilePaths = new javax.swing.JButton();
        btnClearPaths = new javax.swing.JButton();
        errorLabelPaths = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tfExtPrgPath = new javax.swing.JTextField();
        btnChooseExtProgPath = new javax.swing.JButton();
        errorLabelFilePathAndDirectories = new javax.swing.JLabel();
        btnInfoOptionsFilePath = new javax.swing.JButton();
        btnInfoCalFilePath = new javax.swing.JButton();
        btnInfoExternalPrgmPath = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Settings");
        setResizable(false);

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnClear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClear.setText("Clear");

        btnSave.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSave.setText("Save");

        jPanel3.setPreferredSize(new java.awt.Dimension(560, 212));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Server Setting");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Server name:");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, cbUseMachineName, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), tfServerName, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        btnInfoServerName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoicon.png"))); // NOI18N
        btnInfoServerName.setContentAreaFilled(false);
        btnInfoServerName.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoiconfocused.png"))); // NOI18N

        cbUseMachineName.setSelected(true);
        cbUseMachineName.setText("Use Machine name");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Port:");

        btnInfoServerPort.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoicon.png"))); // NOI18N
        btnInfoServerPort.setContentAreaFilled(false);
        btnInfoServerPort.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoiconfocused.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbUseMachineName)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tfServerName, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInfoServerName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tfServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInfoServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfServerName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(btnInfoServerName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbUseMachineName)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(btnInfoServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("MQTT");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Broker address(IP):");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, cbBrokerAddress, org.jdesktop.beansbinding.ELProperty.create("${!selected}"), tfBrokerAddress, org.jdesktop.beansbinding.BeanProperty.create("editable"));
        bindingGroup.addBinding(binding);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Port:");

        cbBrokerAddress.setSelected(true);
        cbBrokerAddress.setText("Use Localhost  IP");

        btnInfoBrokerAddress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoicon.png"))); // NOI18N
        btnInfoBrokerAddress.setContentAreaFilled(false);
        btnInfoBrokerAddress.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoiconfocused.png"))); // NOI18N

        tfBrokerPort.setText("1883");

        btnInfoBrokerPort.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoicon.png"))); // NOI18N
        btnInfoBrokerPort.setContentAreaFilled(false);
        btnInfoBrokerPort.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoiconfocused.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(tfBrokerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnInfoBrokerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbBrokerAddress)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(tfBrokerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(btnInfoBrokerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(44, 44, 44))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfBrokerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(btnInfoBrokerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbBrokerAddress)
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfBrokerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(btnInfoBrokerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        errorLabelServerSettings.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        errorLabelServerSettings.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(errorLabelServerSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(104, 104, 104)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(errorLabelServerSettings)))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Connection Settings", jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Options filepath:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Calibration filepath:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("File path and Directories");

        btnChooseOptionFilePath.setText("...");

        btnChooseCalibrationFilePath.setText("...");

        btnSaveFilePaths.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSaveFilePaths.setText("Save");

        btnClearPaths.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnClearPaths.setText("Clear");

        errorLabelPaths.setForeground(new java.awt.Color(255, 51, 51));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("External Program Path:");

        btnChooseExtProgPath.setText("...");

        errorLabelFilePathAndDirectories.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        errorLabelFilePathAndDirectories.setForeground(new java.awt.Color(255, 0, 0));

        btnInfoOptionsFilePath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoicon.png"))); // NOI18N
        btnInfoOptionsFilePath.setContentAreaFilled(false);
        btnInfoOptionsFilePath.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoiconfocused.png"))); // NOI18N

        btnInfoCalFilePath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoicon.png"))); // NOI18N
        btnInfoCalFilePath.setContentAreaFilled(false);
        btnInfoCalFilePath.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoiconfocused.png"))); // NOI18N

        btnInfoExternalPrgmPath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoicon.png"))); // NOI18N
        btnInfoExternalPrgmPath.setContentAreaFilled(false);
        btnInfoExternalPrgmPath.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/infoiconfocused.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(79, 79, 79)
                                        .addComponent(tfCalFilePath)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnChooseCalibrationFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnInfoCalFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(errorLabelPaths, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(36, 36, 36))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfOptFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnChooseOptionFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInfoOptionsFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(btnClearPaths, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(btnSaveFilePaths, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(51, 51, 51)
                                        .addComponent(tfExtPrgPath))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(errorLabelFilePathAndDirectories)
                                        .addGap(29, 447, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnChooseExtProgPath, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInfoExternalPrgmPath, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(38, 38, 38))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(58, 58, 58)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tfOptFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnChooseOptionFilePath))
                            .addComponent(btnInfoOptionsFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnInfoCalFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(tfCalFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnChooseCalibrationFilePath))
                                .addGap(52, 52, 52)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnInfoExternalPrgmPath, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(tfExtPrgPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnChooseExtProgPath)))))
                        .addGap(68, 68, 68)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSaveFilePaths, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnClearPaths, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(errorLabelFilePathAndDirectories))
                .addGap(26, 26, 26)
                .addComponent(errorLabelPaths)
                .addContainerGap(187, Short.MAX_VALUE))
        );

        btnChooseOptionFilePath.getAccessibleContext().setAccessibleName("cOptFilePath");
        btnChooseOptionFilePath.getAccessibleContext().setAccessibleDescription("");
        btnChooseCalibrationFilePath.getAccessibleContext().setAccessibleName("cCalFilePath");
        btnChooseCalibrationFilePath.getAccessibleContext().setAccessibleDescription("");

        jTabbedPane1.addTab("Files and Directories", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChooseCalibrationFilePath;
    private javax.swing.JButton btnChooseExtProgPath;
    private javax.swing.JButton btnChooseOptionFilePath;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnClearPaths;
    private javax.swing.JButton btnInfoBrokerAddress;
    private javax.swing.JButton btnInfoBrokerPort;
    private javax.swing.JButton btnInfoCalFilePath;
    private javax.swing.JButton btnInfoExternalPrgmPath;
    private javax.swing.JButton btnInfoOptionsFilePath;
    private javax.swing.JButton btnInfoServerName;
    private javax.swing.JButton btnInfoServerPort;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSaveFilePaths;
    private javax.swing.JCheckBox cbBrokerAddress;
    private javax.swing.JCheckBox cbUseMachineName;
    private javax.swing.JLabel errorLabelFilePathAndDirectories;
    private javax.swing.JLabel errorLabelPaths;
    private javax.swing.JLabel errorLabelServerSettings;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField tfBrokerAddress;
    private javax.swing.JTextField tfBrokerPort;
    private javax.swing.JTextField tfCalFilePath;
    private javax.swing.JTextField tfExtPrgPath;
    private javax.swing.JTextField tfOptFilePath;
    private javax.swing.JTextField tfServerName;
    private javax.swing.JTextField tfServerPort;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}