/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trussoptimizater.Truss.ElementModels;

import java.util.ArrayList;
import java.util.Observable;
import trussoptimizater.Truss.TrussModel;

/**
 *
 * @author Chris
 */
abstract public class ObservableArraylist<E> extends Observable implements java.util.Observer {

    protected TrussModel truss;
    protected ArrayList<E> elements;

    public ObservableArraylist(TrussModel truss) {
        this(truss, new ArrayList<E>());
    }

    public ObservableArraylist(TrussModel truss, ArrayList<E> elements) {
        this.elements = elements;
        this.truss = truss;
    }

    public int size() {
        return elements.size();
    }

    public boolean contains(Object o) {
        return this.elements.contains(o);
    }

    public int indexOf(Object o) {
        return this.elements.indexOf(o);
    }

    public E get(int index) {
        return this.elements.get(index);
    }

    public E set(int index, E e) {
        return this.elements.set(index, e);
    }
    
    public final Object[] toArray() {
        return this.elements.toArray();
    }

    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    abstract public void clear();
    //abstract public void add();
}
