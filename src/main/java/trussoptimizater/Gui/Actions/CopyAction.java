package trussoptimizater.Gui.Actions;



import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import javax.swing.*;
import trussoptimizater.*;
import trussoptimizater.Gui.GUI;

public class CopyAction extends AbstractAction {

    private GUI gui;
    private TrussModel truss;

    public CopyAction(String text, String desc, GUI gui, TrussModel truss) {
        this(text,null,desc,gui,truss);
    }

    public CopyAction(String text, ImageIcon icon, String desc, GUI gui, TrussModel truss) {
        this(text,icon,desc,null,gui,truss);

    }

    public CopyAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui, TrussModel truss) {
        super(text, icon);
        this.gui= gui;
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        //copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(gui.getFrame(), "Not Supported Yet", "Alert", JOptionPane.ERROR_MESSAGE);
    }

}