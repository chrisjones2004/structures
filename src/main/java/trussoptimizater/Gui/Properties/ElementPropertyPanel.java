/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trussoptimizater.Gui.Properties;

import java.util.Observable;
import javax.swing.*;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Truss.Analysis.AnalysisMethods;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.Events.ElementEvent;
import trussoptimizater.Truss.Events.ModelEvent;
import trussoptimizater.Truss.Events.TrussEvent;
import trussoptimizater.Truss.TrussModel;

/**
 *
 * @author Chris
 */
abstract public class ElementPropertyPanel<E extends Element> implements java.util.Observer {

    protected TrussModel truss;
    protected GUI gui;
    protected ElementModel<E> model;
    protected boolean update = true;
    protected static final String OTHER = "**OTHER**";
    protected static final String NA = "N/A";
    protected JComponent[] components;
    public static final int MIN_COMBOBOX_WIDTH = 100;

    public ElementPropertyPanel(TrussModel truss, GUI gui, ElementModel<E> model) {
        this.truss = truss;
        this.gui = gui;
        this.model = model;
        model.addObserver(this);
        initComponents();
        setAllComponentToNA();
        enableComponents(false);
    }

    abstract public void initComponents();

    abstract public void updateComponents();

    abstract public JScrollPane getPanel();

    protected void setAllComponentToNA() {
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JTextField) {
                ((JTextField) components[i]).setText(NA);
            }
            if (components[i] instanceof JComboBox) {
                JComboBox cb = (JComboBox) components[i];
                cb.getModel().setSelectedItem(NA);
                cb.getModel().setSelectedItem(NA);
                cb.revalidate();
                cb.repaint();
            }
        }
    }

    protected E getFirstSelectedElement() {
        for (int i = 0; i < model.size(); i++) {
            if (((Element) model.get(i)).isSelected()) {
                return model.get(i);
            }
        }
        return null;
    }

    protected void enableComponents(boolean enabled) {
        for (int i = 0; i < components.length; i++) {
            components[i].setEnabled(enabled);

        }
    }

    public void update(Observable o, Object arg) {
        if (arg instanceof ModelEvent || arg instanceof ElementEvent) {
            TrussEvent ee = (TrussEvent) arg;
            //if (ee.getEventType() != ModelEvent.SELECT_ELEMENTS) {
            //return;
            //}
        } else {
            return;
        }

        if (getFirstSelectedElement() == null) {
            setAllComponentToNA();
            enableComponents(false);
        } else {
            update = false;
            updateComponents();
            enableComponents(true);
            update = true;

        }

    }
}
