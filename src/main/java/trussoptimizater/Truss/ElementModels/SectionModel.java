package trussoptimizater.Truss.ElementModels;

import java.util.ArrayList;
import trussoptimizater.Truss.TrussModel;
import trussoptimizater.Truss.Events.*;
import trussoptimizater.Truss.SectionLibrary;
import trussoptimizater.Truss.Sections.TubularSection;

/**
 * This class represents all the sections being used in the truss model.
 * <p>
 * As java does not allow multiple inherientance this classes uses an inner class called
 * BarObservableArrayList so that both Observable and ArrayList can both be extended.
 * </p>
 *
 * <p>
 * BarModel observes all Bar objects
 * </p>
 * @author Chris
 */
public class SectionModel extends ObservableArraylist<TubularSection>{



    public SectionModel(TrussModel truss) {
        super(truss, new ArrayList<TubularSection>());
        add(SectionLibrary.SECTIONS.get(0));
    }

    /**
     * Loops through sections and tests whether each section is equal to that of
     * "sectionName". If no matching section is found null is returned
     * @param sectionName The name of the section
     * @return A section with a name equal to that of sectionName. If there is no section with the name
     * sectionName then null is returned
     */
    public TubularSection get(String sectionName) {
        for (int i = 0; i < elements.size(); i++) {
            if (sectionName.equals(elements.get(i).getName())) {
                return elements.get(i);
            }
        }
        return null;
    }

    /**
     * Clears existing sections and uses the add method so that each bar is observed by this class
     * @param bars Array of Bar objects
     */
    public void setSections(Object[] sections) {
        this.elements.clear();
        for (int i = 0; i < sections.length; i++) {
            add((TubularSection) sections[i]);
        }
    }

    public void add(String sectioName) {
        TubularSection ts = SectionLibrary.get(sectioName);
        elements.add(ts);
        setChanged();
        notifyObservers(new SectionEvent(ts, SectionEvent.ADD_SECTIONS));
    }

    public void add(TubularSection e) {
        elements.add(e);
        setChanged();
        notifyObservers(new SectionEvent(e, SectionEvent.ADD_SECTIONS));
    }

    public void add(int index, TubularSection element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void remove(int index) {
        TubularSection s = elements.remove(index);
        setChanged();
        notifyObservers(new SectionEvent(s, SectionEvent.REMOVE_SECTIONS) {});
    }

    public void remove(TubularSection o) {
        elements.remove(o);
        setChanged();
        notifyObservers(new SectionEvent((TubularSection)o, SectionEvent.REMOVE_SECTIONS));
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
