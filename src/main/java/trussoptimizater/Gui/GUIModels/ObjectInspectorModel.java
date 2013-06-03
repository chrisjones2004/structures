package trussoptimizater.Gui.GUIModels;

import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.Events.ModelEvent;
import trussoptimizater.Truss.TrussModel;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.*;
import trussoptimizater.Truss.Elements.Node;

// This class takes care of the event listener lists required by TreeModel.
// It also adds "fire" methods that call the methods in TreeModelListener.
// Look in TreeModelSupport for all of the pertinent code.
public class ObjectInspectorModel extends AbstractTreeModel implements TreeModel, java.util.Observer {

    //private ArrayList<Person> peopleArray = new ArrayList<Person>();
    private TrussModel truss;
    public static final String TRUSS_NODE = "Truss";
    public static final String NODES_NODE = "Nodes";
    public static final String BARS_NODE = "Bars";
    public static final String LOADS_NODE = "Loads";
    public static final String SUPPORTS_NODE = "Supports";
    private ArrayList<String> trussChildren = new ArrayList<String>();

    public ObjectInspectorModel(TrussModel truss) {
        super();
        this.truss = truss;
        this.truss.addObserver(this);
    }

    public Object getRoot() {
        return ObjectInspectorModel.TRUSS_NODE;
    }

    public TreePath[] getPaths(ArrayList<Element> elements){
        TreePath[] treePaths = new TreePath[elements.size()];
        for(int i=0;i<elements.size();i++){
            treePaths[i] = getPath(elements.get(i));
            System.out.println(treePaths[i]);
        }
        return treePaths;
    }

    public TreePath getPath(Element element){
        if(element ==null){
            return null;
        }
        String elementType = element.getClass().getSimpleName() + "s";

        if (elementType.equals(ObjectInspectorModel.NODES_NODE)) {
            return new TreePath(new Object[]{getRoot(),ObjectInspectorModel.NODES_NODE,element});
        } else if (elementType.equals(ObjectInspectorModel.BARS_NODE)) {
            return new TreePath(new Object[]{getRoot(),ObjectInspectorModel.BARS_NODE,element});
        } else if (elementType.equals(ObjectInspectorModel.LOADS_NODE)) {
            return new TreePath(new Object[]{getRoot(),ObjectInspectorModel.LOADS_NODE,element});
        } else if (elementType.equals(ObjectInspectorModel.SUPPORTS_NODE)) {
            return new TreePath(new Object[]{getRoot(),ObjectInspectorModel.SUPPORTS_NODE,element});
        }
        return null;
    }

    public Object getChild(Object parent, int index) {
        if (parent.toString().equals(ObjectInspectorModel.TRUSS_NODE)) {
            return trussChildren.get(index);
        } else if (parent.toString().equals(ObjectInspectorModel.NODES_NODE)) {
            return truss.getNodeModel().get(index);
        } else if (parent.toString().equals(ObjectInspectorModel.BARS_NODE)) {
            return truss.getBarModel().get(index);
        } else if (parent.toString().equals(ObjectInspectorModel.LOADS_NODE)) {
            return truss.getLoadModel().get(index);
        } else if (parent.toString().equals(ObjectInspectorModel.SUPPORTS_NODE)) {
            return truss.getSupportModel().get(index);
        }

        return null;
    }

    public int getChildCount(Object parent) {
        if (parent.toString().equals(ObjectInspectorModel.TRUSS_NODE)) {
            return trussChildren.size();
        } else if (parent.toString().equals(ObjectInspectorModel.NODES_NODE)) {
            return truss.getNodeModel().size();
        } else if (parent.toString().equals(ObjectInspectorModel.BARS_NODE)) {
            return truss.getBarModel().size();
        } else if (parent.toString().equals(ObjectInspectorModel.LOADS_NODE)) {
            return truss.getLoadModel().size();
        } else if (parent.toString().equals(ObjectInspectorModel.SUPPORTS_NODE)) {
            return truss.getSupportModel().size();
        }

        return -1;
    }

    public boolean isLeaf(Object node) {
        if (node.toString().equals(ObjectInspectorModel.TRUSS_NODE)) {
            if (trussChildren.size() > 0) {
                return false;
            } else {
                return true;
            }
        } else if (node.toString().equals(ObjectInspectorModel.NODES_NODE)) {
            if (truss.getNodeModel().size() == 0) {
                return true;
            } else {
                return false;
            }
        } else if (node.toString().equals(ObjectInspectorModel.BARS_NODE)) {
            if (truss.getBarModel().size() == 0) {
                return true;
            } else {
                return false;
            }
        } else if (node.toString().equals(ObjectInspectorModel.LOADS_NODE)) {
            if (truss.getLoadModel().size() == 0) {
                return true;
            } else {
                return false;
            }
        } else if (node.toString().equals(ObjectInspectorModel.SUPPORTS_NODE)) {
            if (truss.getSupportModel().size() == 0) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
        System.out.println(path + ", " + newValue);
    }



    public int getIndexOfChild(Object parent, Object child) {
        if (parent.toString().equals(ObjectInspectorModel.TRUSS_NODE)) {
            return trussChildren.indexOf(child);
        } else if (parent.toString().equals(ObjectInspectorModel.NODES_NODE)) {
            return truss.getNodeModel().indexOf(child);
        } else if (parent.toString().equals(ObjectInspectorModel.BARS_NODE)) {
            return truss.getBarModel().indexOf(child);
        } else if (parent.toString().equals(ObjectInspectorModel.LOADS_NODE)) {
            return truss.getLoadModel().indexOf(child);
        } else if (parent.toString().equals(ObjectInspectorModel.SUPPORTS_NODE)) {
            return truss.getSupportModel().indexOf(child);
        }
        return -1;
    }

    public void update(Observable o, Object arg) {
        ModelEvent ee = null;
        ArrayList<Element> elements = null;
        if (!(arg instanceof ModelEvent)) {
            return;
        } else {
            ee = (ModelEvent) arg;
            elements = ee.getElements();
        }
        String elementType = elements.get(0).getClass().getSimpleName() + "s";

        Object[] path = {getRoot(), elementType};
        int[] childIndices = new int[elements.size()];
        Object[] children = new Object[elements.size()];
        for (int i = 0; i < elements.size(); i++) {
            childIndices[i] = elements.get(i).getIndex();
            children[i] = elements.get(i);
        }

        switch (ee.getEventType()) {
            case ModelEvent.ADD_ELEMENTS:
                //if adding the first element add the folder
                if (this.getIndexOfChild(this.getRoot(), elementType) == -1) {
                    Object[] folderPath = {getRoot()};
                    int[] folderChildIndices = {getChildCount(getRoot())};
                    Object[] folderChildren = {elementType};
                    trussChildren.add(elementType);
                    //System.out.println("Adding child " + elementType + " at index " + childIndices[0]);
                    this.fireTreeNodesInserted(new TreeModelEvent(this, folderPath, folderChildIndices, folderChildren));
                }

                //System.out.println("Adding tree node " + children[0] + " at path " + path[0] + "," + path[1] + " and index " + childIndices[0]);
                this.fireTreeNodesInserted(new TreeModelEvent(this, path, childIndices, children));
                break;
            case ModelEvent.REMOVE_ELEMENTS:

                boolean removeFolder = false;
                if (elements.size() == getChildCount(elementType)) {
                    removeFolder = true;
                }
                this.fireTreeNodesRemoved(new TreeModelEvent(this, path, childIndices, children));


                if (removeFolder) {

                    Object[] folderPath = {getRoot()};
                    int[] folderChildIndices = {this.getIndexOfChild(getRoot(), elementType)};
                    Object[] folderChildren = {elementType};
                    //System.out.println("Removing node folder " + folderChildren[0] + " at path " + folderPath[0] + " and index " + folderChildIndices[0]);
                    trussChildren.remove(elementType);
                    this.fireTreeNodesRemoved(new TreeModelEvent(this, folderPath, folderChildIndices, folderChildren));
                }
                break;

        }
    }
}

// This class takes care of the event listener lists required by TreeModel.
// It also adds "fire" methods that call the methods in TreeModelListener.
// Look in TreeModelSupport for all of the pertinent code.
abstract class AbstractTreeModel extends TreeModelSupport implements TreeModel {
}

class TreeModelSupport {

    private Vector vector = new Vector();

    public void addTreeModelListener(TreeModelListener listener) {
        if (listener != null && !vector.contains(listener)) {
            vector.addElement(listener);
        }
    }

    public void removeTreeModelListener(TreeModelListener listener) {
        if (listener != null) {
            vector.removeElement(listener);
        }
    }

    public void fireTreeNodesChanged(TreeModelEvent e) {
        Enumeration listeners = vector.elements();
        while (listeners.hasMoreElements()) {
            TreeModelListener listener = (TreeModelListener) listeners.nextElement();
            listener.treeNodesChanged(e);
        }
    }

    public void fireTreeNodesInserted(TreeModelEvent e) {
        Enumeration listeners = vector.elements();
        while (listeners.hasMoreElements()) {
            TreeModelListener listener = (TreeModelListener) listeners.nextElement();
            listener.treeNodesInserted(e);
        }
    }

    public void fireTreeNodesRemoved(TreeModelEvent e) {
        Enumeration listeners = vector.elements();
        while (listeners.hasMoreElements()) {
            TreeModelListener listener = (TreeModelListener) listeners.nextElement();
            listener.treeNodesRemoved(e);
        }
    }

    public void fireTreeStructureChanged(TreeModelEvent e) {
        Enumeration listeners = vector.elements();
        while (listeners.hasMoreElements()) {
            TreeModelListener listener = (TreeModelListener) listeners.nextElement();
            listener.treeStructureChanged(e);

        }
    }
}
