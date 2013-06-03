package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.Utils;
import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import javax.swing.*;
import trussoptimizater.Gui.GUI;


public class SaveAction extends AbstractAction {

    private GUI gui;
    private TrussModel truss;


    public SaveAction(String text, String desc, GUI gui, TrussModel truss) {
        this(text,null,desc,gui,truss);
    }

    public SaveAction(String text, ImageIcon icon, String desc, GUI gui, TrussModel truss) {
        this(text,icon,desc,null,gui,truss);

    }

    public SaveAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui, TrussModel truss) {
        super(text, icon);
        this.gui= gui;
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        //System.out.println("Saving");
        if (gui.getSavePath() != null) {
            Object[] dataToWrite = {truss.getElementArrays()};
            Utils.writeObjectToFile(gui.getSavePath(), dataToWrite);
            gui.getFrame().setTitle("Truss Optimization - " + gui.getFileChooser().getFileName());

        } else {
            MyActionMap.ACTION_MAP.get(MyActionMap.SAVE_AS_ACTION_KEY).actionPerformed(null);
        }
    }
}