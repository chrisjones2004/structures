package trussoptimizater.Truss.Events;

import java.util.ArrayList;
import trussoptimizater.Truss.Elements.Element;

/**
 * ModelEvent is an event which changes the truss model, ie addition,
 * removal, section of any truss elements
 * @author Chris
 * @param <E>
 */
public class ModelEvent<E extends Element> extends TrussEvent {

    private ArrayList<E> elements;
    public static final int ADD_ELEMENTS = 0;
    public static final int REMOVE_ELEMENTS = 1;
    public static final int SELECT_ELEMENTS = 2;
    public static final int UNSELECT_ELEMENTS = 3;
    public static final int HIGHLIGHT_ELEMENTS = 4;
    public static final int INDEX_CHANGES = 5;
    public static final int ELEMENT_MODIFICATION = 10;


    public ModelEvent(E source, int eventType) {
        super(source, eventType);
        this.elements = new ArrayList<E>();
        elements.add(source);
    }

    public ModelEvent(ArrayList<E> source, int eventType) {
        super(source, eventType);
        this.elements = source;
    }

    public ArrayList<E> getElements() {
        return elements;
    }
}
