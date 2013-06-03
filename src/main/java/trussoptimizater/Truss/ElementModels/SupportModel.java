package trussoptimizater.Truss.ElementModels;

import java.util.ArrayList;
import java.util.Observable;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.Elements.Support;
import trussoptimizater.Truss.Events.*;
import trussoptimizater.Truss.TrussModel;

/**
 * This class represents all the supports in the truss model.
 * <p>
 * As java does not allow multiple inherientance this classes uses an inner class called
 * BarObservableArrayList so that both Observable and ArrayList can both be extended.
 * </p>
 *
 * <p>
 * SupportModel observes all Support objects
 * </p>
 * @author Chris
 */
public class SupportModel extends ElementModel<Support> {

    //private ArrayList<Support> supports = new ArrayList<Support>();
    public SupportModel(TrussModel truss) {
        super(truss, new ArrayList<Support>());
    }

 
}
