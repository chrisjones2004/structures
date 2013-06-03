package trussoptimizater.Gui.Tables;

import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
//import trussoptimizater.Truss.Elements.Joint;

public class PassFailCellRenderer extends DefaultTableCellRenderer {
    /**
     * Fail condition, used when a joint fails certain criteria
     */
    public static final String FAIL = "FAIL";
    /**
     * Pass condition, used when a joint passes certain criteria
     */
    public static final String PASS = "PASS";

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (!table.getModel().isCellEditable(row, column)) {
            c.setBackground(Color.lightGray);
        } else {
            c.setBackground(Color.WHITE);
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }

        try {
            if (table.getValueAt(row, column).toString().equals(PassFailCellRenderer.PASS)) {
                c.setForeground(Color.GREEN);
            } else if (table.getValueAt(row, column).toString().equals(PassFailCellRenderer.FAIL)) {
                c.setForeground(Color.RED);
            } else {
                c.setForeground(Color.BLACK);
            }
        } catch (NumberFormatException e) {
            c.setForeground(Color.BLACK);
        } catch (NullPointerException e) {
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
}
