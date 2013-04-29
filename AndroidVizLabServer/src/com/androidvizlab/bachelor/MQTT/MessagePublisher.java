package com.androidvizlab.bachelor.MQTT;

import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttPersistence;
import com.ibm.mqtt.MqttSimpleCallback;

/**
 *
 * @author The Hive
 */
public class MessagePublisher implements MqttSimpleCallback, Runnable{
    
    //Connection parameters
    private String connString = "tcp://";
    private String brokerName = "localhost";
    private int brokerPortNr = 1883;
    
    //Client
    private String clientID = "QFSA1241Ww";
    private boolean cleanStart = false;
    private short keepalive = 20 * 60;
    
    private MqttClient mqttClient = null;
    
    private MqttPersistence persistence = null;
    
    private int[] qualityofservice = {0};
    
    public MessagePublisher()
    {
        
    }
    
    public void setupConnection()
    {
        connString += brokerName + "@" + brokerPortNr;
        try
        {
            mqttClient = (MqttClient)MqttClient.createMqttClient(connString, persistence);
            mqttClient.registerSimpleHandler(this);
            
            mqttClient.connect(clientID, cleanStart, keepalive);
        }
        catch(MqttException e)
        {
            System.err.println("Mqtt error: Could not connect to broker! " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void publish(String topic, String content)
    {
        try
        {
            int id = mqttClient.publish(topic, content.getBytes(), qualityofservice[0], true);
        }
        catch(MqttException e)
        {
            System.err.println("Mqtt error: Could not publish! " + e.getMessage());
            e.printStackTrace();
        }        
    }
    
    public void disconnect()
    {
        try
        {
            mqttClient.disconnect();
        }
        catch(MqttException e)
        {
            System.err.println("Mqtt error: Could not disconnect! " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void publishArrived(String string, byte[] bytes, int i, boolean bln) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //*** RUNNABLE INTERFACE ***//
    
    @Override
    public void run() {
        this.setupConnection();
    }
    
    //*** SETTERS AND GETTERS ***//
    
    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public int getBrokerPortNr() {
        return brokerPortNr;
    }

    public void setBrokerPortNr(int brokerPortNr) {
        this.brokerPortNr = brokerPortNr;
    }
    
    //JUST FOR TEST
    /*public static void main(String[] args)
    {
        MessagePublisher msgpublisher = new MessagePublisher();
        msgpublisher.setupConnection();
        msgpublisher.publish("test", "This is a test");
        msgpublisher.disconnect();
        System.exit(0);
    }*/
}
