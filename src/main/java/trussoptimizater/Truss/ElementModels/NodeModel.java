package trussoptimizater.Truss.ElementModels;

import java.util.ArrayList;
import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.TrussModel;

/**
 * This class represents all the nodes in the truss model.
 * <p>
 * As java does not allow multiple inherientance this classes uses an inner class called
 * BarObservableArrayList so that both Observable and ArrayList can both be extended.
 * </p>
 *
 * <p>
 * NodeModel observes all Nodes objects
 * </p>
 * @author Chris
 */
public class NodeModel extends ElementModel<Node> {

    //private TrussModel truss;
    //private ArrayList<Node> nodes = new ArrayList<Node>();
    public NodeModel(TrussModel truss) {
        super(truss, new ArrayList<Node>()); //
        //this.truss = truss;
    }


    @Override
    public void remove(int index) {
        Node n = elements.get(index);
        truss.removeInvalidTrussElements(n);
        super.remove(index);
    }

    @Override
    public void remove(Node o) {
        
        super.remove(o);
        truss.removeInvalidTrussElements();

    }

    @Override
    public void removeAll(ArrayList<? extends Node> els) {
        
        super.removeAll(els);
        truss.removeInvalidTrussElements();
    }
}
