package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import trussoptimizater.Gui.MyUndoManager;

public class UndoAction extends AbstractAction {

    private MyUndoManager undoManager;

    public UndoAction(String text, String desc, MyUndoManager undoManager ) {//GUI gui, TrussModel truss
        this(text, null, desc, undoManager);
    }

    public UndoAction(String text, ImageIcon icon, String desc, MyUndoManager undoManager) {  //GUI gui, TrussModel truss
        this(text, icon, desc, null,undoManager);

    }

    public UndoAction(String text, ImageIcon icon, String desc, Integer mnemonic,MyUndoManager undoManager ) {
        super(text, icon);
        //this.gui = gui;
        //this.truss = truss;
        this.undoManager = undoManager;
        this.setEnabled(false);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        //JOptionPane.showMessageDialog(gui.getFrame(), "Not Supported Yet", "Alert", JOptionPane.ERROR_MESSAGE);

        undoManager.setUndoEvent(true);
        try {
            undoManager.getUndoManager().undo();
        } catch (CannotRedoException cre) {
            cre.printStackTrace();
        }
        MyActionMap.ACTION_MAP.get(MyActionMap.UNDO_ACTION_KEY).setEnabled(undoManager.getUndoManager().canUndo());
        MyActionMap.ACTION_MAP.get(MyActionMap.REDO_ACTION_KEY).setEnabled(undoManager.getUndoManager().canRedo());
        undoManager.setUndoEvent(false);
    }
}
