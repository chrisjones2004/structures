/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trussoptimizater.Gui.Tables;

import java.util.ArrayList;
import java.util.Observable;
import javax.swing.*;
import javax.swing.JTable;
import javax.swing.event.ListSelectionListener;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.TrussModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import trussoptimizater.Gui.Actions.MyActionMap;
import trussoptimizater.Gui.Actions.SelectAction;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.Events.ModelEvent;

public abstract class ElementTable<E extends Element> implements java.util.Observer {//extends DynamicTable

    protected TrussModel truss;
    protected GUI gui;
    protected ElementModel<E> model;
    protected JTable table;
    protected JPanel panel;
    protected boolean updateSelectionModel = true;

    public ElementTable(ElementModel<E> model, TrussModel truss, GUI gui) {
        this.truss = truss;
        this.model = model;
        this.gui = gui;
        createGui();
        truss.addObserver(this);
    }

    abstract protected void createGui();

    abstract public JPanel getPanel();

    abstract public JTable getTable();

    public void update(Observable o, Object arg) {
        if(!updateSelectionModel){
            return;
        }



        ModelEvent ee = null;
        ArrayList<Element> elements = null;
        if (!(arg instanceof ModelEvent)) {
            return;
        } else {
            ee = (ModelEvent) arg;
            elements = ee.getElements();
        }

        if(elements.size()>0 && truss.getElementModel(elements.get(0)).equals(model)){
            //ok
        }else{
            return;
        }



        updateSelectionModel = false;
        switch (ee.getEventType()) {
            case ModelEvent.SELECT_ELEMENTS:
                for (int i = 0; i < elements.size(); i++) {
                        //System.out.println("Selecting elements index's "+elements.get(i).getIndex()+ " for table "+elements.get(i).getClass().getSimpleName());
                        try{
                            if(elements.get(i).isSelected()){
                                table.addRowSelectionInterval(elements.get(i).getIndex(), elements.get(i).getIndex());
                            }else{
                                table.removeRowSelectionInterval(elements.get(i).getIndex(), elements.get(i).getIndex());
                            }
                        }catch(IllegalArgumentException e){
                            System.out.println("IllegalArgumentException: trying to set row selection "+elements.get(i).getIndex());
                        }
                        
                }
                break;

        }



        updateSelectionModel = true;
    }

    class ElementTableSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            //Only update truss model selections if user selects rows with mouse + keyboard
            if(!updateSelectionModel){
                return;
            }
            if(event.getValueIsAdjusting()){
                return;
            }


            SelectAction selectAction = (SelectAction) MyActionMap.ACTION_MAP.get(MyActionMap.SELECT_ACTION_KEY);
            
            updateSelectionModel = false;
            selectAction.selectAll(false);
            for (int i = 0; i < table.getSelectedRows().length; i++) {
                int selectedRow = table.getSelectedRows()[i];
                model.get(selectedRow).setSelected(true);
            }
            updateSelectionModel = true;
        }
   }
}
