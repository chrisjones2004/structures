/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trussoptimizater.Gui.GUIModels;

import java.util.ArrayList;
import java.util.Observable;
import javax.swing.*;
import javax.swing.MutableComboBoxModel;
import trussoptimizater.Truss.ElementModels.ObservableArraylist;
import trussoptimizater.Truss.Events.*;

public abstract class ComboBoxModel<E> extends AbstractListModel implements MutableComboBoxModel, java.util.Observer {

    protected Object selectedItem;
    protected ArrayList<E> elements;

    public ComboBoxModel(ObservableArraylist<E> model) {
        this(new ArrayList<E>(), model);
    }

    public ComboBoxModel(ArrayList<E> arrayList, ObservableArraylist<E> model) {
        elements = arrayList;
        model.addObserver(this);
        for (int i = 0; i < model.size(); i++) {
            this.addElement(model.get(i));
        }
    }

    public Object getSelectedItem() {
        return selectedItem;
    }

    public int getSize() {
        return elements.size();
    }

    public Object getElementAt(int i) {
        return elements.get(i).toString();
    }

    public void setSelectedItem(Object newValue) {
        if(newValue.toString().equals(selectedItem)){
            return;
        }

        selectedItem = newValue.toString();
        try {
            int index = elements.indexOf((E) newValue);
            super.fireContentsChanged(((E) newValue).toString(), index, index);
        } catch (ClassCastException e) {
            System.out.println(e);
        }

    }

    public void setElementAt(E newValue, int i) {
        this.fireContentsChanged(newValue, i, i);
        this.elements.set(i, newValue);
    }

    public void addElement(Object obj) {
        elements.add((E) obj);
        this.fireIntervalAdded(obj, this.getSize() - 1, this.getSize() - 1);
    }

    public void addElements(ArrayList<E> objs){
        int index = elements.size();
        elements.addAll(objs);
        this.fireIntervalAdded(objs, index , this.getSize() - 1);
    }

    public void removeElement(Object obj) {
        //System.out.println("Tring to remove " + obj);
        int index = elements.indexOf((E) obj);
        //print();
        elements.remove((E) obj);
        this.fireIntervalRemoved(((E) obj).toString(), index, index);
    }


    //pretty sure this method is wrong, because should it not fire events between first and last element of objs? Or maybe it is just really inefficient
    public void removeElements(ArrayList<E> objs){
        //int index = elements.
        elements.removeAll(objs);
        this.fireIntervalRemoved(((E) objs).toString(), 0, this.getSize());

    }

    public void print() {
        System.out.println("\nPrinting Model");
        for (int i = 0; i < elements.size(); i++) {
            System.out.println(elements.get(i).toString());
        }
    }

    public void insertElementAt(Object obj, int index) {
        elements.add(index, (E) obj);
        this.fireIntervalAdded(((E) obj).toString(), index, index);
    }

    public void removeElementAt(int index) {
        this.fireIntervalRemoved(elements.get(index).toString(), index, index);
        elements.remove(index);

    }

    public boolean contains(E o) {
        return elements.contains(o);
    }

    public final Object[] toArray() {
        return this.elements.toArray();
    }


}
