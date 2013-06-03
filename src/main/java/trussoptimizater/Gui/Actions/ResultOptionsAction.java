package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import javax.swing.*;

import trussoptimizater.*;
import trussoptimizater.Gui.GUI;


public class ResultOptionsAction extends AbstractAction {

    private GUI gui;

    ///*
    public ResultOptionsAction(String text, String desc, GUI gui, TrussModel truss) {
        this(text,null,desc,gui,truss);
    }

    public ResultOptionsAction(String text, ImageIcon icon, String desc, GUI gui, TrussModel truss) {
        this(text,icon,desc,null,gui,truss);

    }

    public ResultOptionsAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui, TrussModel truss) {
        super(text, icon);
        this.gui= gui;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }//*/

    public void actionPerformed(ActionEvent e) {
        gui.getResultsOptionsDialog().showGui();
        gui.getResultsOptionsDialog().getDialog().setLocationRelativeTo(gui.getFrame());
    }
}