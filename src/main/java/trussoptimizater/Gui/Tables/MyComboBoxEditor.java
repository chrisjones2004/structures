package trussoptimizater.Gui.Tables;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;


    public class MyComboBoxEditor extends DefaultCellEditor {
        public MyComboBoxEditor(Object[] items) {
            super(new JComboBox(items));
        }
    }
