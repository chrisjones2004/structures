package trussoptimizater.Gui;

import javax.swing.JTree;
import javax.swing.tree.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class DynamicTree {

    private JTree tree;
    private DefaultTreeModel myTreeModel;
    private DefaultMutableTreeNode myRootNode;
    //private DefaultMutableTreeNode selectedNodes;

    public DynamicTree() {
        myRootNode = new DefaultMutableTreeNode("Structure");
        myTreeModel = new DefaultTreeModel(myRootNode);
        tree = new JTree(myTreeModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        tree.setShowsRootHandles(true);
    }

    public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();
        if (parentPath == null) {
            parentNode = myRootNode;
        } else {
            parentNode = (DefaultMutableTreeNode) parentPath.getLastPathComponent();
        }
        return addObject(parentNode, child, true);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
        return addObject(parent, child, false);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        if (parent == null) {
            parent = myRootNode;
        }
        myTreeModel.insertNodeInto(childNode, parent, parent.getChildCount());

        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    public void removeSelectedObject() {
        DefaultMutableTreeNode removeNode = null;
        DefaultMutableTreeNode parentNode = null;
        TreePath removePath = tree.getSelectionPath();

        if (removePath == null) {
            removeNode = myRootNode;
        } else {
            removeNode = (DefaultMutableTreeNode) removePath.getLastPathComponent();
        }
        parentNode = removeNode.getPreviousSibling();
        removeObject(parentNode, removeNode, true);
    }//end of method

    public void removeObject(DefaultMutableTreeNode parent, DefaultMutableTreeNode child, boolean shouldBeVisible) {

        if (parent == null) {
            parent = myRootNode;
        }
        myTreeModel.removeNodeFromParent(child);

        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(parent.getPath()));
        }
    }//end of removeObject

    public void removeALL() {
        myRootNode.removeAllChildren();
        myTreeModel.reload();
    }

    public JTree getTree() {
        return tree;
    }
}




