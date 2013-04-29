package com.androidvizlab.bachelor.FileWriterAndReader.folder;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Interconnect
 */
public class FileAccessUtility {
    
    //FILE and FILE PATH
    private File file = null;
    private static String FILE_NAME = "serverpreference.text";
    
    //Reader
    private BufferedReader bfr = null;
    private FileReader fReader = null;
    
    //Writer
    private FileWriter fw = null;
    private BufferedWriter bw = null;
    
    public FileAccessUtility()
    {
    
    }
    
    /**
     * Method that reads from a text file
     * @return returns an ArrayList of strings
     */
    public HashMap<String,String> readFromFile()
    {
        HashMap<String,String> map = null;
        
        try 
        {
            map = new HashMap<String,String>();
            
            fReader = new FileReader(FILE_NAME);
            bfr = new BufferedReader(fReader);

            String linje;
            String[] split = new String[2];
            
            while ((linje = bfr.readLine()) != null) {   // null is EOF
                split = linje.split(":");
                map.put(split[0], split[1]);
            }

            fReader.close();
            bfr.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return map;
    }
    
    /**
     * Method that writes to a text file line by line
     * @param param list of items to be written
     */
    public void writeToFile(String... param)
    {
        file = new File(FILE_NAME);
        
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Could not create file!" + ex);
                Logger.getLogger(FileAccessUtility.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try
        {          
            fw = new FileWriter(file.getAbsolutePath());
            bw = new BufferedWriter(fw);
            
            for(int i = 0; i < param.length; i++)
            {
                bw.write(param[i]);
                bw.newLine();
            }
  
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ops! Something went wrong!" + e);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(FileAccessUtility.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileAccessUtility.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
