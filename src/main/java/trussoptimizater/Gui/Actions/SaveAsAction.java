package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.Utils;
import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import javax.swing.*;
import trussoptimizater.Gui.GUI;


public class SaveAsAction extends AbstractAction {

    private GUI gui;
    private TrussModel truss;

    public SaveAsAction(String text, String desc, GUI gui, TrussModel truss) {
        this(text,null,desc,gui,truss);
    }

    public SaveAsAction(String text, ImageIcon icon, String desc, GUI gui, TrussModel truss) {
        this(text,icon,desc,null,gui,truss);

    }

    public SaveAsAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui, TrussModel truss) {
        super(text, icon);
        this.gui= gui;
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Save As");
        if (gui.getFileChooser().showSaveFileChooser()) {
            //only want to exectue if user open something and doesnt click cancel
            gui.setSavePath(gui.getFileChooser().getChoosenFilePath());
            Object[] dataToWrite = { truss.getElementArrays()};
            Utils.writeObjectToFile(gui.getSavePath(), dataToWrite);
            gui.getFrame().setTitle("Truss Optimization - " + gui.getFileChooser().getFileName());
        }
    }
}