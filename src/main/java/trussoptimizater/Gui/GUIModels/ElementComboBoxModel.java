package trussoptimizater.Gui.GUIModels;

import java.util.ArrayList;
import java.util.Observable;
import javax.swing.MutableComboBoxModel;
import trussoptimizater.Truss.ElementModels.ElementModel;
import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.Events.ModelEvent;


public class ElementComboBoxModel<E extends Element> extends ComboBoxModel<E> implements MutableComboBoxModel, java.util.Observer {

    public ElementComboBoxModel(ElementModel model) {
        this(new ArrayList<E>(), model);
    }

    public ElementComboBoxModel(ArrayList<E> arrayList, ElementModel<E> model) {
        super(arrayList, model);

    }

    @Override
    public void print() {
        System.out.println("\nPrinting Model");
        for (int i = 0; i < elements.size(); i++) {
            System.out.println(elements.get(i).toSimpleString());
        }
    }

    @Override
    public Object getElementAt(int i) {
        return elements.get(i).toSimpleString();
    }

    @Override
    public void insertElementAt(Object obj, int index) {
        elements.add(index, (E) obj);
        this.fireIntervalAdded(((E) obj).toSimpleString(), index, index);
    }

    @Override
    public void removeElementAt(int index) {
        this.fireIntervalRemoved(elements.get(index).toSimpleString(), index, index);
        elements.remove(index);
    }

    public void update(Observable o, Object arg) {

        ModelEvent ee = null;
        ArrayList<E> elements = null;

        if (!(arg instanceof ModelEvent)) {
            return;
        } else {
            ee = (ModelEvent) arg;
            elements = ee.getElements();
        }

        switch (ee.getEventType()) {
            case ModelEvent.ADD_ELEMENTS:
                this.addElements(elements);
                break;
            case ModelEvent.REMOVE_ELEMENTS:
                this.removeElements(elements);
                break;

        }
    }

}
