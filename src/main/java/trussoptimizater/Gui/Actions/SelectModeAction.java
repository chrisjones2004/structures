package trussoptimizater.Gui.Actions;


import java.awt.event.*;
import javax.swing.*;
import trussoptimizater.Gui.GUIModels.GUIModeModel;



public class SelectModeAction extends AbstractAction {

    private GUIModeModel guiModeModel;

    public SelectModeAction(String text, String desc, GUIModeModel guiModeModel) {
        this(text,null,desc,guiModeModel);
    }

    public SelectModeAction(String text, ImageIcon icon, String desc, GUIModeModel guiModeModel) {
        this(text,icon,desc,null,guiModeModel);

    }

    public SelectModeAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUIModeModel guiModeModel) {
        super(null, icon);
        this.guiModeModel= guiModeModel;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public void actionPerformed(ActionEvent e) {
        guiModeModel.setMode(GUIModeModel.SELECT_MODE);
        
    }
}
