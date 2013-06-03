
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
public class AddElement<E extends Element> extends ElementUndoableEdit<E> {


    public AddElement(ArrayList<E> elements, ElementModel<E> model, TrussModel truss) {
        super(elements, model,truss);
    }

    @Override
    public void undo() {
        super.undo();
        if(model == null){
            for(int i=0;i<elements.size();i++){
                //System.out.println("Removing  "+elements.get(i)+" from "+truss.getElementModel(elements.get(i)).getClass().getSimpleName());
                truss.getElementModel(elements.get(i)).remove(elements.get(i));
            }
        }else{
            model.removeAll(elements);
        }
        
    }

    @Override
    public void redo() {
        super.redo();


        if(model == null){
            for(int i=0;i<elements.size();i++){
                truss.getElementModel(elements.get(i)).add(elements.get(i));
            }
        }else{
            model.addAll( elements.get(0).getIndex(),elements);
        }

    }
}
