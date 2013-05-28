package com.androidvizlab.bachelor.utilities;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import runtimetest.ExternalProcessHandler;

/**
 *
 * @author The Hive
 * 
 * this class extends FileFilter
 * filters out the most relevant file type that you wish to open or
 * get the absolute path.
 */

public class CustomFileFilter extends FileFilter{

    //SOME STATIC CONSTANTS, FILE EXTENSIONS
    public static final String FILE_EXTENSION_TEXT = "txt";
    public static final String FILE_EXTENSION_DAT = "dat";
    public static final String FILE_EXTENSION_EXE = "exe";
    
    //SOME STATIC CONSTANTS, FILE DESCRIPTIONS
    public static final String FILE_DESC_TEXT = "TEXT file (*.txt)";
    public static final String FILE_DESC_DAT = "DAT file (*.dat)";
    public static final String FILE_DESC_EXE = "EXECUTABLE file (*.exe)";
    
    //FILE EXTENSION AND DESCRIPTIONS
    private String fileFormat = ""; // file type(can add more supported file type)
    protected String description = "";
    
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
            if(f.getName().endsWith(fileFormat))
            {
                return true;
            }
        }
        
        return false;
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
        CustomFileFilter filter = new CustomFileFilter();
        filter.setFileFormat(fileFormat);
        filter.setDescription(description);
        fc.setFileFilter(filter);
        
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
    
    /**
     * Used to find a given directory.
     * @return returns a string value of a given directory's path
     */
    public String getDirectoryPath()
    {
        String directoryPath = "";
        JFileChooser fc = new JFileChooser(startingDirectory);
        fc.setDialogTitle("Select File");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int checker = fc.showOpenDialog(null);
        File f = fc.getSelectedFile();
        try
        {
            if(checker == JFileChooser.APPROVE_OPTION){
                directoryPath = f.getAbsolutePath();
                System.out.println(directoryPath);
            }
        }
        catch(NullPointerException ex)
        {
            System.err.println("file could not be found.");
        }
        return directoryPath;
    }
    
    //GETTERS AND SETTERS
    
    /**
     * see FileFilter doc
     * @return 
     */
    @Override
    public String getDescription() 
    {
       return description;
    }
    
    public void setDescription(String input)
    {
        description = input;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
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
    
    
    /*public static void main(String args[])
    {
       CustomFileFilter filter = new CustomFileFilter();
       filter.setDescription(CustomFileFilter.FILE_EXTENSION_EXE);
       filter.setFileFormat(FILE_EXTENSION_EXE);
       filter.setStartingDirectory("E:\\Interconnect\\My Documents\\eclipse\\");
       ExternalProcessHandler exp = new ExternalProcessHandler();
       String path = filter.getFilePath();
       exp.setExecutableFilePath(path);
       exp.runProcess();
    }*/
}
