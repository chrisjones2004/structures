
package trussoptimizater.Gui.UndoRedoActions;

import java.util.ArrayList;
import javax.swing.undo.AbstractUndoableEdit;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.ElementModels.NodeModel;
import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.TrussModel;

/**
 *
 * @author Chris
 
public class UnselectElement<E extends Element> extends ElementUndoableEdit<E> {

    public UnselectElement(ArrayList<E> elements, ElementModel<E> model, TrussModel truss) {
        super(elements, model, truss);
    }

    @Override
    public void undo() {
        super.undo();
        model.select(elements, true);
    }

    @Override
    public void redo() {
        super.redo();
        model.select(elements, false);
    }
}
*/