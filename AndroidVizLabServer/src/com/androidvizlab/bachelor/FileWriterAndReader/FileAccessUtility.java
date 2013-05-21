package com.androidvizlab.bachelor.FileWriterAndReader;


import com.androidvizlab.bachelor.utilities.CustomFileFilter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author The Hive
 */
public class FileAccessUtility {
    
    //FILE and FILE PATH
    private File file = null;
    private String FILE_NAME = "serverpreference.text";
    
    //Reader
    private BufferedReader bfr = null;
    private FileReader fReader = null;
    
    //Writer
    private FileWriter fw = null;
    private BufferedWriter bw = null;
    
    public FileAccessUtility()
    {
    
    }
    
    public FileAccessUtility(String filename)
    {
        this.FILE_NAME = filename;
    }
    
    public void createFile(String filename)
    {
        try 
        {

            file.createNewFile();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Could not create file!" + ex);
            Logger.getLogger(FileAccessUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int checkFileExist(File file)
    {
        int id = 1;
        
        if(!file.exists())
        {
            int option = JOptionPane.showConfirmDialog(null, "File does not exist!", "Would you like to create the file?", JOptionPane.YES_NO_OPTION);
            
            switch(option)
            {
                case JOptionPane.YES_OPTION:
                    
                    createFile(FILE_NAME);
                    id = 1;
                    break;
                    
                case JOptionPane.NO_OPTION:
                    
                    String input = "";
                    
                    input = JOptionPane.showInputDialog(null,"Write the correct file name.","File does not exist",JOptionPane.OK_CANCEL_OPTION);
                    
                    if(input != null)
                    {
                        FILE_NAME = input;
                        checkFileExist(new File(FILE_NAME));
                    }
                    else if(input != null && input.equals(""))
                    {
                        input = JOptionPane.showInputDialog(null,"Write the correct file name.","File does not exist",JOptionPane.OK_CANCEL_OPTION);
                        FILE_NAME = input;
                        checkFileExist(new File(FILE_NAME));
                    }
                    else
                    {
                        id = -2;
                    }
                    
                    break;
            }
        }
        
        return id;
    }
    
    /**
     * Method that reads from a text file
     * @return returns an HashMap of strings
     */
    public HashMap<String,String> readFromFileKeysAndValues()
    {
        HashMap<String,String> map = new HashMap<String,String>();
        
        file = new File(FILE_NAME);
        
        int id = checkFileExist(file);
        
        switch(id){
            
            case 1:
                
                try 
                {
                    fReader = new FileReader(file);
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
                
            break;
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
    
    public ArrayList<String> getDirectoriesFilePathList(String directoryPath, final String fileExtension)
    {
        File directory = new File(directoryPath);
        
        ArrayList<String> filePathList = null;
        
        if(directory.exists())
        {
            filePathList = new ArrayList<String>();
            
            if(directory.isDirectory())
            {
                //create a FileFilter and override its accept-method
                FileFilter fileFilter = new FileFilter() {

                    public boolean accept(File file) {
                        //if the file extension is .txt return true, else false
                        if (file.getName().endsWith(fileExtension)) {
                            return true;
                        }
                        return false;
                    }
                };
               
                File[] list = directory.listFiles(fileFilter);
                
                for(File  f: list)
                {
                    filePathList.add(f.getAbsolutePath());
                    System.out.println(f.getAbsolutePath());
                }
            }
        }
        return filePathList;
    }
    
    public File getLatestCalibrationSummaryFile(String filepath, final String fileExtension)
    {
        File dir = new File(filepath);
        
        File summary = null;
        
        if(dir.exists())
        {
            if(dir.isDirectory())
            {
                //create a FileFilter and override its accept-method
                FileFilter fileFilter = new FileFilter() {
                    
                    public boolean accept(File file) {
                        //if the file extension is .dat file return true, else false
                        if (file.getName().endsWith(fileExtension) && file.getName().contains("calibration_summary")) 
                        {
                            return true;
                        }
                        return false;
                    }
                };
                
                File[] list  = dir.listFiles(fileFilter);
                
                Arrays.sort(list, new Comparator<File>(){
                    public int compare(File a, File b)
                    {
                        if(a.lastModified() < b.lastModified())
                        {
                            return 1;
                        }
                        else if(a.lastModified() > b.lastModified())
                        {
                            return -1;
                        }
                        return 0;
                    }
                });
                 
                summary = list[0];
            }
        }
        return summary;
    }
    
    /*
    public static void main(String args[])
    {
        FileAccessUtility fau = new FileAccessUtility();
        
        File f = fau.getLatestCalibrationSummaryFile("src//com//androidvizlab//bachelor//calibrationandoptionsfile", CustomFileFilter.FILE_EXTENSION_DAT);
        
        System.out.print(f.getName());
    }
    */
}