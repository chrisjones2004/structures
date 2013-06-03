package trussoptimizater.Gui.Tables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Observable;
import javax.swing.*;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import trussoptimizater.Gui.Actions.MyActionMap;
import trussoptimizater.Gui.Actions.SelectAction;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.GUIModels.NodeTableModel;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.TrussModel;
import trussoptimizater.Truss.ElementModels.NodeModel;

public class NodeTable extends ElementTable<Node> implements java.util.Observer{



    private final String[] columnToolTips = {
        "Node Number",
        "X Cordinate in (m)",
        "Z Cordinate in (m)",
        "Does the node have a Point Load on it",
        "Does the node have a support on it"};

    public NodeTable(NodeModel model, TrussModel truss, GUI gui) {
        super(model, truss, gui);
    }


    protected void createGui(){

        JButton removeButton = new JButton("Remove Row(s)");
        removeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                gui.getUndoManager().setCompoundUnadoableEdit(true);
                for (int i = 0; i < table.getSelectedRows().length; i++) {
                    model.remove(table.getSelectedRows()[i] - i);
                }
                gui.getUndoManager().setCompoundUnadoableEdit(false);
                table.revalidate();
                table.repaint();
            }
        });
        JButton addButton = new JButton("add Row ");
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                model.add(new Node(model.size()+1));
                table.revalidate();
                table.repaint();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,5));
        buttonPanel.add(removeButton);
        buttonPanel.add(addButton);

        table = new JTable(new NodeTableModel(truss)) {

            //Implement table header tool tips.
            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {

                    @Override
                    public String getToolTipText(MouseEvent e) {
                        java.awt.Point p = e.getPoint();
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex =
                        columnModel.getColumn(index).getModelIndex();
                        return columnToolTips[realIndex];
                    }
                };
            }
        };
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(20);
        table.setGridColor(Color.black);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setIntercellSpacing(new Dimension(1,1));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setShowGrid(true);
        table.getSelectionModel().addListSelectionListener(new ElementTableSelectionListener());
        

        TableColumn col = table.getColumnModel().getColumn(NodeTableModel.NODE_NUMBER_COLOUMN_INDEX);
        col.setCellRenderer(new GeneralCellRenderer());
        col.setPreferredWidth(50);

        col = table.getColumnModel().getColumn(NodeTableModel.X_CORDINATE_COLUMN_INDEX);
        col.setCellRenderer(new GeneralCellRenderer());
        col.setPreferredWidth(100);

        col = table.getColumnModel().getColumn(NodeTableModel.Z_CORDINATE_COLUMN_INDEX);
        col.setCellRenderer(new GeneralCellRenderer());
        col.setPreferredWidth(100);

        col = table.getColumnModel().getColumn(NodeTableModel.LOADED_COLUMN_INDEX);
        col.setPreferredWidth(100);

        col = table.getColumnModel().getColumn(NodeTableModel.SUPPORTED_COLUMN_INDEX);
        col.setPreferredWidth(100);

        //adjusting JTable header
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setVerticalAlignment(SwingConstants.TOP);
        JTableHeader header = table.getTableHeader();
        Dimension dim = header.getPreferredSize();
        dim.height *=2;
        header.setPreferredSize(dim);


        panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

    }



    /*public void setHeaderRenderer(){
        MultiLineHeaderRenderer mlhr = new MultiLineHeaderRenderer();
        Enumeration e = table.getColumnModel().getColumns();
        while (e.hasMoreElements()) {
            TableColumn col = ((TableColumn) e.nextElement());
            col.setHeaderRenderer(mlhr);
        }
    }*/

    public JPanel getPanel() {
        return panel;
    }


    public JTable getTable() {
        return table;
    }





}


