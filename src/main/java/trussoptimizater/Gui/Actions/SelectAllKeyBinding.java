package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import javax.swing.*;

import trussoptimizater.*;
import trussoptimizater.Gui.GUI;

public class SelectAllKeyBinding extends AbstractAction {

    private TrussModel truss;
    
   public SelectAllKeyBinding(TrussModel truss){
        this(null,null,truss);
    }

    public SelectAllKeyBinding(String text, String desc, TrussModel truss) {
        super(text);
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        selectAll();
    }
    
    public void selectAll(){
        SelectAction selectAction = (SelectAction) MyActionMap.ACTION_MAP.get(MyActionMap.SELECT_ACTION_KEY);
        selectAction.selectAll(true);

    }
}
