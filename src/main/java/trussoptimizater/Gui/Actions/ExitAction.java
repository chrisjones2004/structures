package trussoptimizater.Gui.Actions;

import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Point2D;
import javax.swing.*;

import trussoptimizater.Gui.*;

public class ExitAction extends AbstractAction {

    private GUI gui;

    public ExitAction(String text, String desc, GUI gui) {
        this(text,null,desc,gui);
    }

    public ExitAction(String text, ImageIcon icon, String desc, GUI gui) {
        this(text,icon,desc,null,gui);

    }

    public ExitAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui) {
        super(text, icon);
        this.gui= gui;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));

    }

    public void actionPerformed(ActionEvent e) {
        Object[] options = {"Save and Quit", "Quit", "Cancel"};
        int n = JOptionPane.showOptionDialog(gui.getFrame(), "Do you want to save your project?", "Alert", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        if (n == 0) {
            MyActionMap.ACTION_MAP.get(MyActionMap.SAVE_ACTION_KEY).actionPerformed(null);
            System.exit(0);
        } else if (n == 1) {
            System.exit(0);
        }
    }
}