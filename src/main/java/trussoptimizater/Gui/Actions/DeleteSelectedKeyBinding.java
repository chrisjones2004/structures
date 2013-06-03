package trussoptimizater.Gui.Actions;

import java.awt.event.*;
import javax.swing.*;

public class DeleteSelectedKeyBinding extends AbstractAction {

    public DeleteSelectedKeyBinding(String text, String desc) {
        /*super(text);
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);*/
        this(text, null, desc);
    }

    public DeleteSelectedKeyBinding(String text, ImageIcon icon, String desc) {
        super(null, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        DeleteAction deleteAction = (DeleteAction) MyActionMap.ACTION_MAP.get(MyActionMap.DELETE_ACTION_KEY);
        deleteAction.deleteAllSelectedElements();
    }
}