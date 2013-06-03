package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import javax.swing.*;

import trussoptimizater.*;
import trussoptimizater.Gui.GUI;

public class ViewOptionsAction extends AbstractAction {

    private GUI gui;


    public ViewOptionsAction(String text, String desc, GUI gui, TrussModel truss) {
        this(text,null,desc,gui,truss);
    }

    public ViewOptionsAction(String text, ImageIcon icon, String desc, GUI gui, TrussModel truss) {
        this(text,icon,desc,null,gui,truss);

    }

    public ViewOptionsAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui, TrussModel truss) {
        super(text, icon);
        this.gui= gui;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public void actionPerformed(ActionEvent e) {
        gui.getViewOptionsDialog().showGui();
        gui.getViewOptionsDialog().getDialog().setLocationRelativeTo(gui.getFrame());
        //gui.getView().repaint();
    }
}
