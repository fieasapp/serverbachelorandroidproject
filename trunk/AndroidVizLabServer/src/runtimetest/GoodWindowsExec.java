package runtimetest;

import java.io.File;


public class GoodWindowsExec
{
    public static void main(String args[])
    {
       /* if (args.length < 1)
        {
            System.out.println("USAGE: java GoodWindowsExec <cmd>");
            System.exit(1);
        }
    */
        try
        {    /*        
            String osName = System.getProperty("os.name" );
            String[] cmd = new String[3];
            if( osName.equals( "Windows NT (unknown)" ) )
            {
                cmd[0] = "cmd.exe" ;
                cmd[1] = "/C" ;
                cmd[2] = args[0];
            }
            else if( osName.equals( "Windows 95" ) )
            {
                cmd[0] = "command.com" ;
                cmd[1] = "/C" ;
                cmd[2] = args[0];
            }
           
            
            System.out.println("Execing " + cmd[0] + " " + cmd[1] 
                               + " " + cmd[2]);
            */
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("java RuntimeTest",null,new File("C:\\Users\\The Hive\\Desktop\\runtimetest"));
            // any error message?
            StreamGobbler errorGobbler = new 
                StreamGobbler(proc.getErrorStream(), "ERROR");            
            
            // any output?
            StreamGobbler outputGobbler = new 
                StreamGobbler(proc.getInputStream(), "OUTPUT");
                
            // kick them off
            errorGobbler.start();
            outputGobbler.start();
                                    
            // any error???
            int exitVal = proc.waitFor();
            System.out.println("ExitValue: " + exitVal);        
        } catch (Throwable t)
          {
            t.printStackTrace();
          }
    }
}