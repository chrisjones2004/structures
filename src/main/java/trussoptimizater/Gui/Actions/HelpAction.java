package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import javax.swing.*;

import trussoptimizater.*;
public class HelpAction extends AbstractAction {


    private TrussModel truss;


    public HelpAction( TrussModel truss){
        this(null,null,null,truss);
    }

    public HelpAction(String text, String desc,  TrussModel truss) {
        this(text,null,desc,truss);
    }

    public HelpAction(String text, ImageIcon icon, String desc,  TrussModel truss) {
        this(text,icon,desc,null,truss);

    }

    public HelpAction(String text, ImageIcon icon, String desc, Integer mnemonic,  TrussModel truss) {
        super(text, icon);
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public void actionPerformed(ActionEvent e) {
        //To do
    }


}
