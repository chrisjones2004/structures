package trussoptimizater.Truss.Events;

import java.util.ArrayList;
import java.util.EventObject;
import trussoptimizater.Truss.Sections.TubularSection;

public class SectionEvent<E extends TubularSection>  extends TrussEvent {
    private ArrayList<E> elements;
    public static final int ADD_SECTIONS = 5;
    public static final int REMOVE_SECTIONS = 6;

    public SectionEvent(E source, int eventType) {
        super(source, eventType);
        this.elements = new ArrayList<E>();
        elements.add(source);
    }

    public SectionEvent(ArrayList<E> source, int eventType) {
        super(source, eventType);
        this.elements = source;
    }

    public ArrayList<E> getSections() {
        return elements;
    }
}
