package runtimetest;

import com.androidvizlab.bachelor.Interface.SimpleObservable;
import java.io.File;

/**
 *
 * @author The Hive
 */
public class ExternalProcessHandler extends SimpleObservable 
implements Runnable{
    
    private boolean processStarted = false;
    private int processExitVal = 100;
    private String executableFilePath = "C:\\Users\\The Hive\\Desktop\\runtimetest";
    
    public ExternalProcessHandler()
    {
    }
    
    public int runProcess()
    {
        try
        {    
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("java RuntimeTest",null,new File("src//runtimetest"));
            //Process proc = rt.exec(executableFilePath);
            
            // any error message?
            StreamIOCapture error_capture = new 
            StreamIOCapture(proc.getErrorStream(), "ERROR");            
            
            // any output?
            StreamIOCapture output_capture = new 
            StreamIOCapture(proc.getInputStream(), "OUTPUT");
                
            // kick them off
            error_capture.start();
            output_capture.start();
                                 
            //Notify Observer that the process has started
            setProcessStarted(true);
            
            // any error???
            int exitVal = proc.waitFor();
           
            setProcessExitVal(exitVal);
        } 
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        
        return processExitVal;
    }

    public String getExecutableFilePath() {
        return executableFilePath;
    }

    public void setExecutableFilePath(String executableFilePath) {
        this.executableFilePath = executableFilePath;
    }
    
    public boolean hasStarted()
    {
        return processStarted;
    }
    
    public void setProcessStarted(boolean started)
    {
        processStarted = started;
        notifyObservers("PROCESS_STARTED");
    }
    
    public int getProcessExitVal()
    {
        return processExitVal;
    }
    
    public void setProcessExitVal(int value)
    {
        processExitVal = value;
        
        if(processExitVal < 0)
        {
            System.out.println("Program exited with error:\nExit value: " + value);
            notifyObservers("PROCESS_EXITED_WITH_ERROR");
        }else{  
            System.out.println("Program exited normally:\nExit value: " + value);
            notifyObservers("PROCESS_EXITED_NORMAL");
        }
    }
    
    //*** RUNNABLE INTERFACE ***//
    
    @Override
    public void run() 
    {
        runProcess(); //start the process
    }
    
    //JUST FOR TEST
    /*public static void main(String args[])
    {
       ExternalProcessHandler exp = new ExternalProcessHandler();
       exp.runProcess();
    }*/
}
