package trussoptimizater.Gui;

import java.io.*;
import javax.swing.*;
import trussoptimizater.Gui.GUI;

/**
 * This class is responsible for opening and saving projects.
 * @author Chris
 */
public class FileChooser  {

    private JFileChooser fc;
    private String path = null;
    private String fileName = null;
    private GUI gui;
    
    public FileChooser(GUI gui) { 
        this.gui = gui;
        fc = new JFileChooser();
    }

    /**
     * This method is used when user wishes to open a project.
     * @return True if user has selects "Open". False if user selects "Cancel"
     */
    public boolean showOpenFileChooser(){
            int returnVal = fc.showOpenDialog(gui.getFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                path = file.getAbsolutePath();
                fileName = file.getName();
                return true;
            }else {
                System.out.println("Open command cancelled by user.");
                return false;
            }
            
    }
    
    /**
     * This method is used when user wishes to save a project.
     * @return True if user has selects "Save". False if user selects "Cancel"
     */
    public boolean showSaveFileChooser(){
            int returnVal = fc.showSaveDialog(gui.getFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                path = file.getAbsolutePath();
                fileName = file.getName();
                return true;
            }else {
                System.out.println("Save command cancelled by user.");
                return false;
            }
    }


    /**
     *
     * @return an absolute path to the project data.
     */
    public String getChoosenFilePath(){
        return this.path;
    }

    /**
     * This method is used to set the title of the main JFrame. The filename should always end in ".ser"
     * @return the filename of the project.
     */
    public String getFileName(){
        if(fileName.endsWith(".ser")){
            return this.fileName;
        }else{
            return this.fileName + ".ser";
        }
        
    }

}