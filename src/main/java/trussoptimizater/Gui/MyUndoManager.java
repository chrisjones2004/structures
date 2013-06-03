/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trussoptimizater.Gui;

import java.util.ArrayList;
import java.util.Observable;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoManager;
import trussoptimizater.Gui.Actions.MyActionMap;
import trussoptimizater.Gui.UndoRedoActions.AddElement;
import trussoptimizater.Gui.UndoRedoActions.RemoveElement;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.Events.ModelEvent;
import trussoptimizater.Truss.TrussModel;

/**
 *
 * @author Chris
 */
public class MyUndoManager implements java.util.Observer {

    private UndoManager undoManager = new UndoManager();
    private TrussModel truss;
    private boolean undoEvent = false;
    private ArrayList<ModelEvent> compoundUnadoableEdits;
    private boolean compoundUnadoableEdit = false;
    public static final String ADD_COMPOUND_UNDO_EVENTS = "ADD_COMPOUND_UNDO_EVENTS";

    public MyUndoManager(TrussModel truss) {
        this.truss = truss;
        truss.addObserver(this);
        compoundUnadoableEdits = new ArrayList<ModelEvent>();
    }

    public void setCompoundUnadoableEdit(boolean compoundUnadoableEdit) {
        this.compoundUnadoableEdit = compoundUnadoableEdit;
        if (!compoundUnadoableEdit) {
            System.out.println("Adding "+compoundUnadoableEdits.size()+" compound edits");
            update(null, ADD_COMPOUND_UNDO_EVENTS);
        }
    }

    public ArrayList<Element> getCompoundElements(){
    ArrayList<Element> elements = new ArrayList<Element>();
    for(int i = 0;i<compoundUnadoableEdits.size();i++){
        elements.addAll(compoundUnadoableEdits.get(i).getElements());
    }

    return elements;
}

    public void update(Observable o, Object arg) {


        if(arg.toString().equals(ADD_COMPOUND_UNDO_EVENTS)){
            //ok
        }else if(arg instanceof ModelEvent){
            ModelEvent ee = (ModelEvent) arg;

            if (ee.getEventType() != ModelEvent.ADD_ELEMENTS
                    && ee.getEventType() != ModelEvent.REMOVE_ELEMENTS) {
                return;
            }
        }else{
            return;
        }

        if (undoEvent) {
            return;
        } 

        if (compoundUnadoableEdit) {
            ModelEvent ee = (ModelEvent) arg;
            System.out.println("Adding compound edit "+ee.getElements().get(0).getClass().getSimpleName());
            compoundUnadoableEdits.add(ee);
            return;
        }

        int eventType = -1;
        ElementModel em = null;
        ArrayList<Element> elements = null;
        if(arg.toString().equals(ADD_COMPOUND_UNDO_EVENTS) ){
            if(compoundUnadoableEdits.isEmpty()){
                return;
            }
            eventType = compoundUnadoableEdits.get(0).getEventType();
            elements = getCompoundElements();
            System.out.println("Undoing compound edit "+elements);
        }else{
            ModelEvent ee = (ModelEvent) arg;
            eventType = ee.getEventType();
            elements = new ArrayList<Element>();
            elements.addAll(ee.getElements());
            em = truss.getElementModel(elements.get(0));
        }
        
        if (elements.isEmpty()) {
            return;
        }

        



        switch (eventType) {
            case ModelEvent.ADD_ELEMENTS:
                System.out.println("undoable add added ");
                AddElement<Element> ae = new AddElement<Element>(elements, em,truss);
                undoManager.undoableEditHappened(new UndoableEditEvent(elements, ae));
                break;
            case ModelEvent.REMOVE_ELEMENTS:
                System.out.println("undoable remove added ");
                RemoveElement<Element> re = new RemoveElement<Element>(elements, em,truss);
                undoManager.undoableEditHappened(new UndoableEditEvent(elements, re));
                break;
            /*case ModelEvent.SELECT_ELEMENTS:
                System.out.println("undoable select added ");
                SelectElement<Element> se = new SelectElement<Element>(elements, em,truss);
                undoManager.undoableEditHappened(new UndoableEditEvent(elements, se));
                break;
            case ModelEvent.UNSELECT_ELEMENTS:
                System.out.println("undoable unselect added ");
                UnselectElement<Element> use = new UnselectElement<Element>(elements, em,truss);
                undoManager.undoableEditHappened(new UndoableEditEvent(elements, use));
                break;
             *
             */

        }
        compoundUnadoableEdits.clear();
        MyActionMap.ACTION_MAP.get(MyActionMap.UNDO_ACTION_KEY).setEnabled(undoManager.canUndo());
        MyActionMap.ACTION_MAP.get(MyActionMap.REDO_ACTION_KEY).setEnabled(undoManager.canRedo());
    }

    public UndoManager getUndoManager() {
        return undoManager;
    }

    public void setUndoEvent(boolean undoEvent) {
        this.undoEvent = undoEvent;
    }

}
