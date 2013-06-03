/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trussoptimizater.Gui.GUIModels;

import java.util.ArrayList;
import java.util.Observable;
import javax.swing.table.AbstractTableModel;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.Events.ModelEvent;
import trussoptimizater.Truss.TrussModel;

public abstract class ElementTableModel<E extends Element> extends AbstractTableModel implements java.util.Observer {

    protected TrussModel truss;
    protected ElementModel<E> model;

    public ElementTableModel(TrussModel truss, ElementModel<E> model) {
        this.truss = truss;
        this.model = model;
        this.model.addObserver(this);
    }

    public int getRowCount() {
        return model.size();
    }

    public void update(Observable o, Object arg) {
        ModelEvent ee = null;
        ArrayList<E> elements = null;
        int index;
        if (!(arg instanceof ModelEvent)) {
            return;
        } else {
            ee = (ModelEvent) arg;
            elements = ee.getElements();
        }

        switch (ee.getEventType()) {
            case ModelEvent.ADD_ELEMENTS:

                for(int i = 0;i<elements.size();i++){
                    index = elements.get(i).getIndex();
                    this.fireTableRowsInserted(index, index);
                }

                break;
            case ModelEvent.REMOVE_ELEMENTS:
                
                for(int i = elements.size()-1;i>=0;i--){
                    index = elements.get(i).getIndex() ;
                    //System.out.println("Removing index "+index+" from "+model.getClass().getSimpleName());
                    this.fireTableRowsDeleted(index, index);
                    
                }
                break;

        }
        
    }
}
