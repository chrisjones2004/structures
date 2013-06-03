package trussoptimizater.Gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.TreeSet;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import trussoptimizater.Gui.GUIModels.GUIPanelModel;
import trussoptimizater.Gui.GUIModels.ObjectInspectorModel;
import trussoptimizater.Truss.Elements.Bar;
import trussoptimizater.Truss.Elements.Element;
import trussoptimizater.Truss.Elements.Load;
import trussoptimizater.Truss.Elements.Node;
import trussoptimizater.Truss.Elements.Support;
import trussoptimizater.Truss.Events.ModelEvent;
import trussoptimizater.Truss.TrussModel;

public class ObjectInspectorPanel extends OptionPanel implements java.util.Observer {

    private static final String TITLE = "Object Inspector";
    private TrussModel truss;
    private GUI gui;
    private JTree tree;
    private ObjectInspectorModel inspectorModel;
    private boolean updateSelectionModel = true;

    public ObjectInspectorPanel(TrussModel t, GUI g) {
        super();
        this.truss = t;
        this.truss.addObserver(this);
        this.gui = g;
        inspectorModel = new ObjectInspectorModel(truss);


        tree = new JTree(inspectorModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        tree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {

                if (!updateSelectionModel) {
                    System.out.println("This is NOT a user event");
                    return;
                }


                updateSelectionModel = false;
                TreePath[] paths = tree.getSelectionPaths();
                if (paths == null) {
                    return;
                }
                if (paths.length == 1) {
                    truss.getNodeModel().selectAll(false);
                    truss.getBarModel().selectAll(false);
                    truss.getSupportModel().selectAll(false);
                    truss.getLoadModel().selectAll(false);
                }

                paths = e.getPaths();
                boolean select;
                // Iterate through all affected nodes
                for (int i = 0; i < paths.length; i++) {

                    if (!(paths[i].getLastPathComponent() instanceof Element)) {
                        continue;
                    }

                    if (e.isAddedPath(i)) {
                        select = true;
                    } else if(tree.getSelectionModel().isPathSelected(paths[i])){
                        select = true;
                    }else{
                        select = false;
                    }

                    Element element = (Element) paths[i].getLastPathComponent();
                    if (element instanceof Node) {
                        truss.getNodeModel().get(element.getIndex()).setSelected(select);
                    } else if (element instanceof Bar) {
                        truss.getBarModel().get(element.getIndex()).setSelected(select);
                    } else if (element instanceof Load) {
                        truss.getLoadModel().get(element.getIndex()).setSelected(select);
                    } else if (element instanceof Support) {
                        truss.getSupportModel().get(element.getIndex()).setSelected(select);
                    }
                }
                updateSelectionModel = true;
            }
        });
    }

    @Override
    protected JComponent getMainPanel() {
        return new JScrollPane(tree);
    }

    @Override
    protected String getPanelTitle() {
        return TITLE;
    }

    @Override
    protected Action getCloseAction() {
        return new closeAction();
    }

    public JTree getTree() {
        return tree;
    }

    public ObjectInspectorModel getInspectorModel() {
        return inspectorModel;
    }

    /**
     * Used to update selected nodes on tree
     * @param o
     * @param arg
     */
    public void update(Observable o, Object arg) {

        if (!(arg instanceof ModelEvent)) {
            return;
        }

        ModelEvent ee = (ModelEvent) arg;

        if (ee.getEventType() != ModelEvent.SELECT_ELEMENTS) {
            return;
        }
        if(updateSelectionModel){
            updateSelectionModel = false;
            ArrayList<Element> elements = ee.getElements();
            //tree.setSelectionPaths();
            TreePath[] paths = inspectorModel.getPaths(elements);
            for(int i = 0;i<elements.size();i++){
                if(elements.get(i).isSelected()){
                    tree.addSelectionPath(paths[i]);
                }else{
                    tree.removeSelectionPath(paths[i]);
                }
            }

            updateSelectionModel = true;
        }

    }

    class closeAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            gui.getGuiPanelModel().setSidePanel(GUIPanelModel.SIDE_PANEL_NOT_VISIBLE);
        }
    }
}
