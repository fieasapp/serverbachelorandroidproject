package runtimetest;

import java.io.*;
/**
 * This is used to capture streams from an external process
 * @author The Hive
 */
public class StreamIOCapture extends Thread{
    
    InputStream is;
    String type;
    
    public StreamIOCapture(){}
    
    public StreamIOCapture(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }
    
    /**
     * main run method for execution on a thread
     */
    @Override
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            String line = null;
            
            while ( (line = br.readLine()) != null)
            {
                System.out.println(type + ">" + line);    
            } 
        }
        catch (IOException ioe)
        {
          ioe.printStackTrace();  
        }
    }
}
