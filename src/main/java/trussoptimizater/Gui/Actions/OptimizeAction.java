package trussoptimizater.Gui.Actions;
import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import javax.swing.*;
import trussoptimizater.Gui.GUI;





public class OptimizeAction extends AbstractAction {

    private GUI gui;


    public OptimizeAction( GUI gui){
        this(null,null,null,gui);
    }

    public OptimizeAction(String text, String desc,  GUI gui) {
        this(text,null,desc,gui);
    }

    public OptimizeAction(String text, ImageIcon icon, String desc,  GUI gui) {
        this(text,icon,desc,null,gui);

    }

    public OptimizeAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui) {
        super(text, icon);
        this.gui = gui;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke("F7"));
    }

    public void actionPerformed(ActionEvent e) {
        gui.getOptimizeDialog().getDialog().setLocationRelativeTo(gui.getFrame());
        gui.getOptimizeDialog().getDialog().setVisible(true);
    }

}
