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
import trussoptimizater.Gui.GUIModels.SectionComboBoxModel;
import trussoptimizater.Gui.SwingUtils;
import trussoptimizater.Truss.Analysis.AnalysisMethods;
import trussoptimizater.Truss.Elements.Bar;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.Events.ModelEvent;
import trussoptimizater.Truss.Events.TrussEvent;
import trussoptimizater.Truss.Sections.TubularSection;
import trussoptimizater.Truss.TrussModel;

public class BarsPropertiesPanel extends ElementPropertyPanel<Bar> {

    
    private ElementComboBoxModel node1ComboBoxModel;
    private ElementComboBoxModel node2ComboBoxModel;
    private SectionComboBoxModel sectionComboBoxModel;

    public BarsPropertiesPanel(TrussModel truss, GUI gui) {
        super(truss, gui, truss.getBarModel());
    }

    public void initComponents() {
        node1ComboBoxModel = new ElementComboBoxModel(truss.getNodeModel());
        node2ComboBoxModel = new ElementComboBoxModel(truss.getNodeModel());
        sectionComboBoxModel = new SectionComboBoxModel(truss.getSectionModel());


        String[] barRestraints = {Bar.PINNED_PINNED_RESTRAINT, Bar.FIXED_FIXED_RESTRAINT};
        //JComboBox node1ComboBox = new JComboBox(getNodeStringArray());
        JComboBox node1ComboBox = new JComboBox(node1ComboBoxModel);//new NodeComboBoxModel(truss.getNodeModel())

        node1ComboBox.setMinimumSize(new Dimension(MIN_COMBOBOX_WIDTH, node1ComboBox.getPreferredSize().height));
        node1ComboBox.setPreferredSize(new Dimension(MIN_COMBOBOX_WIDTH, node1ComboBox.getPreferredSize().height));
        node1ComboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {

                JComboBox cb = (JComboBox) e.getSource();
                if (node1ComboBoxModel.getSelectedItem().toString().equals(NA) || node1ComboBoxModel.getSelectedItem().toString().equals(OTHER)) {
                    return;
                }
                if(!update){
                    return;
                }
                Node node1 = truss.getNodeModel().get(cb.getSelectedIndex());
                for (int i = 0; i < truss.getBarModel().size(); i++) {
                    if (truss.getBarModel().get(i).isSelected()) {
                        truss.getBarModel().get(i).setNode1(node1);
                        //truss.getBarModel().get(i).updateBar();
                    }
                }
            }
        });


        JComboBox node2ComboBox = new JComboBox(node2ComboBoxModel);//new NodeComboBoxModel(truss.getNodeModel())
        node2ComboBox.setMinimumSize(new Dimension(MIN_COMBOBOX_WIDTH, node2ComboBox.getPreferredSize().height));
        node2ComboBox.setPreferredSize(new Dimension(MIN_COMBOBOX_WIDTH, node2ComboBox.getPreferredSize().height));

        node2ComboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {

                JComboBox cb = (JComboBox) e.getSource();

                if (node2ComboBoxModel.getSelectedItem().toString().equals(NA) || node2ComboBoxModel.getSelectedItem().toString().equals(OTHER)) {
                    return;
                }
                if(!update){
                    return;
                }
                Node node2 = truss.getNodeModel().get(cb.getSelectedIndex());
                for (int i = 0; i < truss.getBarModel().size(); i++) {
                    if (truss.getBarModel().get(i).isSelected()) {
                        truss.getBarModel().get(i).setNode2(node2);
                        //truss.getBarModel().get(i).updateBar();
                    }
                }
            }
        });

        JComboBox sectionComboBox = new JComboBox(sectionComboBoxModel);//gui.getSectionComboBoxModel(
        sectionComboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {

                if (sectionComboBoxModel.getSelectedItem().toString().equals(NA) || sectionComboBoxModel.getSelectedItem().toString().equals(OTHER)) {
                    return;
                }
                if(!update){
                    return;
                }
                TubularSection section = truss.getSectionModel().get(sectionComboBoxModel.getSelectedItem().toString());
                for (int i = 0; i < truss.getBarModel().size(); i++) {
                    if (truss.getBarModel().get(i).isSelected()) {
                        truss.getBarModel().get(i).setSection(section);
                    }
                }
            }
        });

        JComboBox restraintComboBox = new JComboBox(barRestraints);
        restraintComboBox.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                JComboBox cb = (JComboBox) e.getSource();

                if (cb.getSelectedItem().toString().equals(NA) || cb.getSelectedItem().toString().equals(OTHER)) {
                    return;
                }
                if(!update){
                    return;
                }
                String restraint = cb.getSelectedItem().toString();
                for (int i = 0; i < truss.getBarModel().size(); i++) {
                    if (truss.getBarModel().get(i).isSelected()) {
                        truss.getBarModel().get(i).setRestraint(restraint);
                    }
                }
            }
        });



        components = new JComponent[]{node1ComboBox, node2ComboBox, sectionComboBox, restraintComboBox};//

    }

    @Override
    public void updateComponents() {
        Bar firstSelectedBar = getFirstSelectedElement();
        setAllComponentToNA();
        //System.out.println("Bar 2 restraint is " + firstSelectedBar.getRestraint());
        for (int i = firstSelectedBar.getIndex(); i < truss.getBarModel().size(); i++) {

            if (!truss.getBarModel().get(i).isSelected()) {
                continue;
            }

            if (firstSelectedBar.getNode1().getIndex() != truss.getBarModel().get(i).getNode1().getIndex()) {
                //((JComboBox) components[0]).setSelectedItem(OTHER);
                this.node1ComboBoxModel.setSelectedItem(OTHER);


                //((JTextField) components[0]).setText(OTHER);
            }
            if (firstSelectedBar.getNode2().getIndex() != truss.getBarModel().get(i).getNode2().getIndex()) {
                //((JComboBox) components[1]).setSelectedItem(OTHER);
                this.node2ComboBoxModel.setSelectedItem(OTHER);
            }

            if (!firstSelectedBar.getSection().getName().equals(truss.getBarModel().get(i).getSection().getName())) {
                this.sectionComboBoxModel.setSelectedItem(OTHER);
            }

            if (!firstSelectedBar.getRestraint().equals(truss.getBarModel().get(i).getRestraint())) {
                ((JComboBox) components[3]).getModel().setSelectedItem(OTHER);
            }
        }

        if (!((JComboBox) components[0]).getSelectedItem().toString().equals(OTHER)) {
            //System.out.println("Setting component 0 to " + firstSelectedBar.getNode1().getIndex());
            //((JComboBox) components[0]).getModel().setSelectedNodeItem(firstSelectedBar.getNode1());
            this.node1ComboBoxModel.setSelectedItem(firstSelectedBar.getNode1().toSimpleString());
        }

        if (!((JComboBox) components[1]).getSelectedItem().toString().equals(OTHER)) {
            //((JComboBox) components[1]).getModel().setSelectedNodeItem(firstSelectedBar.getNode2());
            this.node2ComboBoxModel.setSelectedItem(firstSelectedBar.getNode2().toSimpleString());
        }

        JComboBox sectionBox = ((JComboBox) components[2]);
        if (sectionBox.getSelectedItem() != null && !sectionBox.getSelectedItem().toString().equals(OTHER)) {
            this.sectionComboBoxModel.setSelectedItem(firstSelectedBar.getSection().getName());
        }

        JComboBox restraintBox = ((JComboBox) components[3]);
        if (restraintBox.getSelectedItem() != null && !restraintBox.getSelectedItem().toString().equals(OTHER)) {
            //this.sectionComboBoxModel.setSelectedItem(firstSelectedBar.getSection().getName());
            ((JComboBox) components[3]).getModel().setSelectedItem(firstSelectedBar.getRestraint());
        }
        //if (!((JComboBox) components[3]).getSelectedItem().toString().equals(OTHER)) {
            //((JComboBox) components[3]).setSelectedItem(firstSelectedBar.getRestraint());
        //}



        //System.out.println(((JComboBox) components[2]).getSelectedItem());
        //System.out.println(((JComboBox) components[1]).getSelectedItem());

        /*for (int i = 0; i < components.length; i++) {
        components[i].revalidate();
        components[i].repaint();
        }*/

    }

    public JScrollPane getPanel() {
        String[] barLabels = {"Node 1: ", "Node 2:", "Section:", "restraint:"};
        JPanel barPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        barPanel.add(SwingUtils.getJLabelColumn(barLabels));
        barPanel.add(SwingUtils.getComponentColumn(components));
        //barPanel.setBorder(new TitledBorder(lineborder, "Bars"));

        JScrollPane scrollPane = new JScrollPane(barPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }

    @Override
    public void update(Observable o, Object arg) {
        super.update(o,arg);
        if (getFirstSelectedElement() != null) {
            if (truss.getAnalysisMethods().getAnalysisMethod() == AnalysisMethods.FRAME_STIFFNESS_METHOD) {
                components[3].setEnabled(true);
            } else {
                components[3].setEnabled(false);
            }
        }

    }
}
