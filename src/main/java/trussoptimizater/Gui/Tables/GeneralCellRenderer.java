package trussoptimizater.Gui.Tables;

import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class GeneralCellRenderer extends DefaultTableCellRenderer {

    @Override
    public void setValue(Object value) {
        DecimalFormat DF = new DecimalFormat("#.####");
        try {
            setText((value == null) ? "" : DF.format(value));
        } catch (IllegalArgumentException ex) {
        }
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


        if (!table.getModel().isCellEditable(row, column)) {
            c.setBackground(Color.LIGHT_GRAY);
        } else {
            c.setBackground(Color.WHITE);
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }

        int[] selectedRows = table.getSelectedRows();

        for (int i = 0; i < selectedRows.length; i++) {
            if (row == selectedRows[i]) {
                c.setBackground(table.getSelectionBackground());
            }
        }

        setHorizontalAlignment(SwingConstants.CENTER);
        return c;
    }
}//end of class
