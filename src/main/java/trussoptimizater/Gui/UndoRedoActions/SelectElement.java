package trussoptimizater.Gui.UndoRedoActions;

import java.util.ArrayList;
import javax.swing.undo.AbstractUndoableEdit;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.ElementModels.NodeModel;
import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.TrussModel;

/*
public class SelectElement<E extends Element> extends ElementUndoableEdit<E> {

    public SelectElement(ArrayList<E> elements, ElementModel<E> model, TrussModel truss) {
        super(elements, model, truss);
    }

    @Override
    public void undo() {
        super.undo();
        if (model == null) {
            for (int i = 0; i < elements.size(); i++) {
                elements.get(i).setSelected(false);
            }
        } else {
            model.select(elements, false);
        }
    }

    @Override
    public void redo() {
        super.redo();
        if (model == null) {
            for (int i = 0; i < elements.size(); i++) {
                elements.get(i).setSelected(true);
            }
        } else {
            model.select(elements, true);
        }

    }
}
*/