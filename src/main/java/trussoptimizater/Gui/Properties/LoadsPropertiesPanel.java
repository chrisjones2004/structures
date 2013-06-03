/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trussoptimizater.Gui.Properties;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.*;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.GUIModels.ElementComboBoxModel;
import trussoptimizater.Gui.GUIModels.GUIPanelModel;
import trussoptimizater.Gui.OptionPanel;
import trussoptimizater.Gui.StackedBox;
import trussoptimizater.Gui.SwingUtils;
import trussoptimizater.Truss.Analysis.AnalysisMethods;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.Elements.Bar;
import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.Elements.Load;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.Elements.Support;
import trussoptimizater.Truss.Sections.TubularSection;
import trussoptimizater.Truss.TrussModel;


public class LoadsPropertiesPanel extends ElementPropertyPanel<Load> {

    private ElementComboBoxModel nodeComboBoxModel;

    public LoadsPropertiesPanel(TrussModel truss, GUI gui) {
        super(truss, gui, truss.getLoadModel());
    }

    public void initComponents() {
        String[] loadDirections = {Load.VERTICAL_LOAD, Load.HORIZOANTAL_LOAD};
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
                //update = false;

                if(!update){
                    return;
                }
                Node node = truss.getNodeModel().get(cb.getSelectedIndex());
                for (int i = 0; i < truss.getLoadModel().size(); i++) {
                    if (truss.getLoadModel().get(i).isSelected()) {
                        truss.getLoadModel().get(i).setNode(node);
                    }
                }
                //update = true;
            }
        });


        JTextField loadTextField = new JTextField(8);
        loadTextField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField) e.getSource();
                //update = false;

                if(!update){
                    return;
                }
                try {
                    double valueToSet = Double.parseDouble(tf.getText());
                    for (int i = 0; i < truss.getLoadModel().size(); i++) {
                        if (truss.getLoadModel().get(i).isSelected()) {
                            truss.getLoadModel().get(i).setLoad(valueToSet);
                        }
                    }

                } catch (NumberFormatException nf) {
                    System.out.println("Only double values are allowed in node propert box " + nf);
                }
                //update = true;
            }
        });


        JComboBox directionsComboBox = new JComboBox(loadDirections);
        directionsComboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                JComboBox cb = (JComboBox) e.getSource();

                if (cb.getSelectedItem().toString().equals(NA) || cb.getSelectedItem().toString().equals(OTHER)) {
                    return;
                }
                if(!update){
                    return;
                }

                String direction = cb.getSelectedItem().toString();
                for (int i = 0; i < truss.getLoadModel().size(); i++) {
                    if (truss.getLoadModel().get(i).isSelected()) {
                        truss.getLoadModel().get(i).setOrientation(direction);
                    }
                }
            }
        });

        components = new JComponent[]{nodeComboBox, loadTextField, directionsComboBox};

    }



    public JScrollPane getPanel() {

        String[] loadLabels = {"Node: ", "Load:", "Direction:"};
        String[] loadUnits = {"", "KN", ""};
        JPanel loadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        loadPanel.add(SwingUtils.getJLabelColumn(loadLabels));
        loadPanel.add(SwingUtils.getComponentColumn(components));
        loadPanel.add(SwingUtils.getJLabelColumn(loadUnits));
        //loadPanel.setBorder(new TitledBorder(lineborder, "Loads"));
        JScrollPane scrollPane = new JScrollPane(loadPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }



    @Override
    public void updateComponents() {
        Load firstSelectedLoad = getFirstSelectedElement();
        setAllComponentToNA();


        for (int i = firstSelectedLoad.getIndex(); i < truss.getLoadModel().size(); i++) {

            if (!truss.getLoadModel().get(i).isSelected()) {
                continue;
            }

            if (firstSelectedLoad.getNode().getIndex() != truss.getLoadModel().get(i).getNode().getIndex()) {
                //((JComboBox) components[0]).setSelectedItem(OTHER);
                this.nodeComboBoxModel.setSelectedItem(OTHER);
            }

            if (firstSelectedLoad.getLoad() != truss.getLoadModel().get(i).getLoad()) {
                ((JTextField) components[1]).setText(OTHER);
            }

            if (!firstSelectedLoad.getOrientation().equals(truss.getLoadModel().get(i).getOrientation())) {
                ((JComboBox) components[2]).getModel().setSelectedItem(OTHER);
            }
        }

        if (!((JComboBox) components[0]).getSelectedItem().equals(OTHER)) {
            //((JComboBox) components[0]).setSelectedIndex(firstSelectedLoad.getNode().getIndex());
            this.nodeComboBoxModel.setSelectedItem(firstSelectedLoad.getNode().toSimpleString());
        }

        if (!((JTextField) components[1]).getText().equals(OTHER)) {
            ((JTextField) components[1]).setText(Double.toString(firstSelectedLoad.getLoad()));
        }

        if (!((JComboBox) components[2]).getSelectedItem().equals(OTHER)) {
            ((JComboBox) components[2]).getModel().setSelectedItem(firstSelectedLoad.getOrientation());
        }


    }

}
