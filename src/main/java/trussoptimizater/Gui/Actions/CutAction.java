package trussoptimizater.Gui.Actions;


import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import javax.swing.*;
import trussoptimizater.*;
import trussoptimizater.Gui.GUI;

public class CutAction extends AbstractAction {

    private GUI gui;
    private TrussModel truss;

    public CutAction(String text, String desc, GUI gui, TrussModel truss) {
        this(text,null,desc,gui,truss);
    }

    public CutAction(String text, ImageIcon icon, String desc, GUI gui, TrussModel truss) {
        this(text,icon,desc,null,gui,truss);

    }

    public CutAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui, TrussModel truss) {
        super(text, icon);
        this.gui= gui;
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        //cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(gui.getFrame(), "Not Supported Yet", "Alert", JOptionPane.ERROR_MESSAGE);
    }

}