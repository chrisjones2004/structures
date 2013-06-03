package trussoptimizater.Gui.Actions;

import java.awt.event.*;
import javax.swing.*;
import trussoptimizater.Gui.GUI;
public class EscapeKeyBinding extends AbstractAction {

    private GUI gui;

    public EscapeKeyBinding(GUI gui, String text, String desc) {
        this(gui, text, null, desc);
    }

    public EscapeKeyBinding(GUI gui,String text, ImageIcon icon, String desc) {
        super(null, icon);
        this.gui = gui;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        gui.getView().getViewModel().setCurrentlyDrawingBar(false);
    }
}