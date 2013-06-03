/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trussoptimizater.Gui.Properties;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.*;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.SwingUtils;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.Events.ModelEvent;
import trussoptimizater.Truss.Events.TrussEvent;
import trussoptimizater.Truss.TrussModel;

/**
 *
 * @author Chris
 */
public class NodesPropertiesPanel extends ElementPropertyPanel<Node> {

    public NodesPropertiesPanel(TrussModel truss, GUI gui) {
        super(truss, gui, truss.getNodeModel());
    }

    public void initComponents() {

        JTextField xTextField = new JTextField(8);
        xTextField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField) e.getSource();
                if(!update){
                    return;
                }
                try {
                    double valueToSet = Double.parseDouble(tf.getText());
                    for (int i = 0; i < truss.getNodeModel().size(); i++) {
                        if (truss.getNodeModel().get(i).isSelected()) {
                            truss.getNodeModel().get(i).setX(valueToSet * 100);
                        }
                    }
                    //truss.updateAllBars();
                } catch (NumberFormatException nf) {
                    System.out.println("Only double values are allowed in node propert box " + nf);
                }

            }
        });
        JTextField zTextField = new JTextField(8);
        zTextField.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField) e.getSource();
                if(!update){
                    return;
                }
                try {
                    double valueToSet = Double.parseDouble(tf.getText());
                    for (int i = 0; i < truss.getNodeModel().size(); i++) {
                        if (truss.getNodeModel().get(i).isSelected()) {
                            truss.getNodeModel().get(i).setZ(valueToSet * 100);
                        }
                    }
                } catch (NumberFormatException nf) {
                    System.out.println("Only double values are allowed in node propert box " + nf);
                }
            }
        });

        components = new JComponent[]{xTextField, zTextField};
    }

    public void updateComponents() {
        Node firstSelectedNode = getFirstSelectedElement();
        setAllComponentToNA();



        for (int i = firstSelectedNode.getIndex(); i < truss.getNodeModel().size(); i++) {

            if (!truss.getNodeModel().get(i).isSelected()) {
                continue;
            }

            if (firstSelectedNode.getX() != truss.getNodeModel().get(i).getX()) {
                ((JTextField) components[0]).setText(OTHER);
            }
            if (firstSelectedNode.getZ() != truss.getNodeModel().get(i).getZ()) {
                ((JTextField) components[1]).setText(OTHER);
            }
        }
        if (!((JTextField) components[0]).getText().equals(OTHER)) {
            ((JTextField) components[0]).setText(Double.toString(firstSelectedNode.getX() / 100));
        }

        if (!((JTextField) components[1]).getText().equals(OTHER)) {
            ((JTextField) components[1]).setText(Double.toString(firstSelectedNode.getZ() / 100));
        }

    }

    public JScrollPane getPanel() {


        String[] nodeLabels = {"X:", "Z:"};
        String[] unitLabels = {"m", "m"};
        JPanel nodePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        nodePanel.add(SwingUtils.getJLabelColumn(nodeLabels));
        nodePanel.add(SwingUtils.getComponentColumn(components));
        nodePanel.add(SwingUtils.getJLabelColumn(unitLabels));

        JScrollPane scrollPane = new JScrollPane(nodePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return scrollPane;
    }


}
