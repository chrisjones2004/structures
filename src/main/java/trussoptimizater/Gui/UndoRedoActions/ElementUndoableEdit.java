/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trussoptimizater.Gui.UndoRedoActions;

import java.util.ArrayList;
import javax.swing.undo.AbstractUndoableEdit;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.TrussModel;

/**
 *
 * @author Chris
 */
abstract public class ElementUndoableEdit<E extends Element> extends AbstractUndoableEdit{

    protected ElementModel<E> model;
    protected ArrayList<E> elements;
    protected TrussModel truss;

    public ElementUndoableEdit(ArrayList<E> elements, ElementModel<E> model,TrussModel truss) {
        super();
        this.model = model;
        this.elements = elements;
        this.truss = truss;
    }
}
