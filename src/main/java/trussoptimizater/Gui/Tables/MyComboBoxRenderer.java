package trussoptimizater.Gui.Tables;

import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;

public class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {

    public MyComboBoxRenderer(Object[] items) {
        super(items);
    }

    public MyComboBoxRenderer(ComboBoxModel cb) {
        super(cb);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }

        // Select the current value
        setSelectedItem(value);
        return this;
    }
}



