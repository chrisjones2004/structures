/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trussoptimizater.Gui.Properties;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import javax.swing.*;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.GUIModels.ElementComboBoxModel;
import trussoptimizater.Gui.SwingUtils;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.Elements.Support;
import trussoptimizater.Truss.TrussModel;



public class SupportsPropertiesPanel extends ElementPropertyPanel<Support> {

    private ElementComboBoxModel nodeComboBoxModel;

    public SupportsPropertiesPanel(TrussModel truss, GUI gui) {
        super(truss, gui, truss.getSupportModel());
    }

    public void initComponents() {

        String[] supportConditions = {Support.FREE, Support.FIXED, OTHER, NA};
        nodeComboBoxModel = new ElementComboBoxModel(truss.getNodeModel());
        JComboBox nodeComboBox = new JComboBox(nodeComboBoxModel);
        nodeComboBox.setMinimumSize(new Dimension(MIN_COMBOBOX_WIDTH, nodeComboBox.getPreferredSize().height));
        nodeComboBox.setPreferredSize(new Dimension(MIN_COMBOBOX_WIDTH, nodeComboBox.getPreferredSize().height));
        nodeComboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                JComboBox cb = (JComboBox) e.getSource();

                if (cb.getSelectedItem().toString().equals(NA) || cb.getSelectedItem().toString().equals(OTHER)) {
                    return;
                }

                if(!update){
                    return;
                }
                Node node = truss.getNodeModel().get(cb.getSelectedIndex());
                for (int i = 0; i < truss.getSupportModel().size(); i++) {
                    if (truss.getSupportModel().get(i).isSelected()) {
                        truss.getSupportModel().get(i).setNode(node);
                    }
                }
            }
        });



        JComboBox uxComboBox = new JComboBox(supportConditions);
        uxComboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                JComboBox cb = (JComboBox) e.getSource();

                if (cb.getSelectedItem().toString().equals(NA) || cb.getSelectedItem().toString().equals(OTHER)) {
                    return;
                }

                if(!update){
                    return;
                }
                String restraint = cb.getSelectedItem().toString();
                for (int i = 0; i < truss.getSupportModel().size(); i++) {
                    if (truss.getSupportModel().get(i).isSelected()) {
                        truss.getSupportModel().get(i).setUx(restraint);
                    }
                }
            }
        });





        JComboBox uzComboBox = new JComboBox(supportConditions);
        uzComboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                JComboBox cb = (JComboBox) e.getSource();

                if (cb.getSelectedItem().toString().equals(NA) || cb.getSelectedItem().toString().equals(OTHER)) {
                    return;
                }
                if(!update){
                    return;
                }
                String restraint = cb.getSelectedItem().toString();
                for (int i = 0; i < truss.getSupportModel().size(); i++) {
                    if (truss.getSupportModel().get(i).isSelected()) {
                        truss.getSupportModel().get(i).setUz(restraint);
                    }
                }
            }
        });

        JComboBox ryComboBox = new JComboBox(supportConditions);
        ryComboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                JComboBox cb = (JComboBox) e.getSource();

                if (cb.getSelectedItem().toString().equals(NA) || cb.getSelectedItem().toString().equals(OTHER)) {
                    return;
                }
                if(!update){
                    return;
                }
                String restraint = cb.getSelectedItem().toString();
                for (int i = 0; i < truss.getSupportModel().size(); i++) {
                    if (truss.getSupportModel().get(i).isSelected()) {
                        truss.getSupportModel().get(i).setRy(restraint);
                    }
                }
            }
        });

        components = new JComponent[]{nodeComboBox, uxComboBox, uzComboBox, ryComboBox};

    }




    public JScrollPane getPanel() {
        String[] supportLabels = {"Node:", "UX:", "UZ:", "RY:"};
        JPanel supportPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        supportPanel.add(SwingUtils.getJLabelColumn(supportLabels));
        supportPanel.add(SwingUtils.getComponentColumn(components));
        //supportPanel.setBorder(new TitledBorder(lineborder, "Supports"));
        JScrollPane scrollPane = new JScrollPane(supportPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }


    @Override
    public void updateComponents() {
        Support firstSelectedSupport= getFirstSelectedElement();
        setAllComponentToNA();

        for (int i = firstSelectedSupport.getIndex(); i < truss.getSupportModel().size(); i++) {

            if (!truss.getSupportModel().get(i).isSelected()) {
                continue;
            }

            if (firstSelectedSupport.getNode().getIndex() != truss.getSupportModel().get(i).getNode().getIndex()) {
                //((JComboBox) components[0]).setSelectedItem(OTHER);
                this.nodeComboBoxModel.setSelectedItem(OTHER);
            }
            if (!firstSelectedSupport.getUx().equals(truss.getSupportModel().get(i).getUx())) {
                ((JComboBox) components[1]).getModel().setSelectedItem(OTHER);
            }

            if (!firstSelectedSupport.getUz().equals(truss.getSupportModel().get(i).getUz())) {
                ((JComboBox) components[2]).getModel().setSelectedItem(OTHER);
            }

            if (!firstSelectedSupport.getRy().equals(truss.getSupportModel().get(i).getRy())) {
                ((JComboBox) components[3]).getModel().setSelectedItem(OTHER);
            }
        }

        if (!((JComboBox) components[0]).getSelectedItem().toString().equals(OTHER)) {
            //((JComboBox) components[0]).getModel().setSelectedIndex(firstSelectedSupport.getNode().getIndex());
            this.nodeComboBoxModel.setSelectedItem(firstSelectedSupport.getNode().toSimpleString());
        }

        if (!((JComboBox) components[1]).getSelectedItem().toString().equals(OTHER)) {
            ((JComboBox) components[1]).getModel().setSelectedItem(firstSelectedSupport.getUx());
        }

        if (!((JComboBox) components[2]).getSelectedItem().toString().equals(OTHER)) {
            ((JComboBox) components[2]).getModel().setSelectedItem(firstSelectedSupport.getUz());
        }

        if (!((JComboBox) components[3]).getSelectedItem().toString().equals(OTHER)) {
            ((JComboBox) components[3]).getModel().setSelectedItem(firstSelectedSupport.getRy());
        }
    }
}
