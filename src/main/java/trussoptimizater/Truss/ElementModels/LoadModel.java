package trussoptimizater.Truss.ElementModels;

import java.util.ArrayList;
import trussoptimizater.Truss.Elements.Load;
import trussoptimizater.Truss.TrussModel;


/**
 * This class represents all the loads in the truss model.
 * <p>
 * As java does not allow multiple inherientance this classes uses an inner class called
 * BarObservableArrayList so that both Observable and ArrayList can both be extended.
 * </p>
 *
 * <p>
 * BarModel observes all Load objects
 * </p>
 * @author Chris
 */
/**
 * This inner clases extends ObservableArrayList functionalty so that:
 * <ul>
 * <li>when an element is removed all element numbers are updated.
 * <li>When an element is added, this class will observe it
 * </ul>
 * @param <E> Load
 */
public class LoadModel extends ElementModel<Load> {

    //private LoadTableModel barTableModel = new LoadTableModel();
    //private ArrayList<Load> loads = new ArrayList<Load>();
    //private TrussModel truss;
    public LoadModel(TrussModel truss) {
        super(truss, new ArrayList<Load>());
    }


    @Override
    public void remove(int index) {
        
        Load load = (Load) get(index);
        load.getNode().getLoadIndexes().remove((Integer) load.getIndex());
        super.remove(index);
    }

    @Override
    public void remove(Load o) {
        
        Load load = (Load) o;
        load.getNode().getLoadIndexes().remove((Integer) load.getIndex());
        super.remove(o);
    }


}
