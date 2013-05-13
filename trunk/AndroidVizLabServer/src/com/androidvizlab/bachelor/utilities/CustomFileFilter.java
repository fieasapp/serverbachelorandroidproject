package com.androidvizlab.bachelor.utilities;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author The Hive
 * 
 * this class extends FileFilter
 * filters out the most relevant file type that you wish to open or
 * get the absolute path.
 */

public class CustomFileFilter extends FileFilter{

    private String[] fileFormat = {"dat","txt"}; // file type(can add more supported file type)
    private String[] descriptions = {"DAT file (*.dat) ", "TXT file (*.txt)"};
    protected String description = "";
    private boolean isDatFile = false;
    
    private char dotIndex = '.';
    private String startingDirectory = "src/com/androidvizlab/bachelor/calibrationandoptionsfile"; //starting directory where the given file type is likely to be found.
    
    /**
     * file filter class method: filters out file type that is accepted by this filter
     * @param f
     * @return 
     */
    @Override
    public boolean accept(File f) {
        if(f.isDirectory())
        {
            return true;
        }
        else if(f.isFile())
        {
            for(int i = 0; i < fileFormat.length; i++)
            {
                if(f.getName().endsWith(fileFormat[i]))
                {
                    description = descriptions[i];
                    return true;
                }
            }
        }
        
        return false;
    }

    
    /**
     * see FileFilter doc
     * @return 
     */
    @Override
    public String getDescription() 
    {
       return description;
    }
    
    
    /**
     * helper method to determined the extension of a file
     * @param f
     * @return 
     */
    public String extension(File f)
    {
        String fileName = f.getName();
        int indexFile = fileName.lastIndexOf(dotIndex);
        
        if(indexFile > 0 && indexFile < fileName.length()-1)
        {
            return fileName.substring(indexFile+1);
        }
        else
        {
            return "";
        }
    }
    
    /**
     * Method that opens a window where you can select files and find out which file you have selected
     * @return String filename with absolute path
     */
    public String getFilePath()
    {
        String filename = "";
        JFileChooser fc = new JFileChooser(startingDirectory);
        fc.setDialogTitle("Select File");
        fc.setFileFilter(new CustomFileFilter());
        
        int checker = fc.showOpenDialog(null);
        File f = fc.getSelectedFile();
        try
        {
            if(checker == JFileChooser.APPROVE_OPTION){
                filename = f.getAbsolutePath();
                System.out.println(filename);
            }
        }
        catch(NullPointerException ex)
        {
            System.err.println("file could not be found.");
        }
        return filename;
    }
    
    
    public String getStartingDirectory() {
        return startingDirectory;
    }

    /**
     * this method enable you to set the starting directory where
     * the given file is located
     * @param startingDirectory 
     */
    public void setStartingDirectory(String startingDirectory) {
        this.startingDirectory = startingDirectory;
    }
}
