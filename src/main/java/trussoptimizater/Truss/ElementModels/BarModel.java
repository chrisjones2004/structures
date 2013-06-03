package trussoptimizater.Truss.ElementModels;

import java.util.ArrayList;
import java.util.Observable;
import trussoptimizater.Truss.Elements.Bar;
import trussoptimizater.Truss.TrussModel;
import trussoptimizater.Truss.Events.*;

/**
 * This class represents all the bars in the truss model.
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
public class BarModel extends ElementModel<Bar> {

    //private ArrayList<Bar> bars = ;
    public BarModel(TrussModel truss) {
        super(truss, new ArrayList<Bar>());
    }

}
