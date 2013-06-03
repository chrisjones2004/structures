package trussoptimizater.Truss.Events;

import java.util.ArrayList;
import trussoptimizater.Truss.Elements.Element;

/**
 * This event is fired if the inner contents of an element are changed
 * @author Chris
 * @param <E>
 */
public  class ElementEvent<E extends Element> extends TrussEvent {

    private Element element;
    //public static final int ELEMENT_MODIFICATION = 10;
    //public static final int SELECT_ELEMENTS = 12;
    //public static final int UNSELECT_ELEMENTS = 13;
    //public static final int HIGHLIGHT_ELEMENTS = 14;
    //public static final int INDEX_CHANGES = 15;

    public ElementEvent(E source, int eventType){
        super(source, eventType);
        this.element = source;
    }

    public Element getElements() {
        return element;
    }

}

