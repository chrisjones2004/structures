package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import javax.swing.*;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Truss.Elements.Node;

public class ScaleAction extends AbstractAction {

    private GUI gui;
    private TrussModel truss;
    private Node nodeAnchor;
    private double scaleX;
    private double scaleZ;

    public ScaleAction(GUI gui, TrussModel truss) {
        this(null, null, null, gui, truss);
    }

    public ScaleAction(String text, String desc, GUI gui, TrussModel truss) {
        this(text, null, desc, gui, truss);
    }

    public ScaleAction(String text, ImageIcon icon, String desc, GUI gui, TrussModel truss) {
        this(text, icon, desc, null, gui, truss);

    }

    public ScaleAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui, TrussModel truss) {
        super(text, icon);
        this.gui = gui;
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public void actionPerformed(ActionEvent e) {
        gui.getScaleDialog().getDialog().setLocationRelativeTo(gui.getFrame());
        gui.getScaleDialog().getDialog().setVisible(true);
        //gui.getMyGlassPane().setDrawCursor(false);

    }

    public void scale() {
        AffineTransform at = new AffineTransform();
        //at.rotate(angle, nodeAnchor.x, nodeAnchor.z);
        at.translate(nodeAnchor.x, nodeAnchor.z);
        at.scale(scaleX, scaleZ);
        Point2D rotatedPoint = new Point2D.Double();
        for (int i = 0; i < truss.getNodeModel().size(); i++) {
            if (truss.getNodeModel().get(i).isSelected()) {
                at.transform(truss.getNodeModel().get(i).getPoint2D(), rotatedPoint);
                truss.getNodeModel().get(i).setPoint(rotatedPoint);
            }
        }

        /*for (int i = 0; i < truss.getBarModel().size(); i++) {
            truss.getBarModel().get(i).updateBar();
        }*/

    }

    public void setNodeAnchor(Node nodeAnchor) {
        this.nodeAnchor = nodeAnchor;
    }

    /*Set angle in radians*/
    public void setXScale(double scaleX) {
        this.scaleX = scaleX;
    }
    
    public void setZScale(double scaleZ) {
        this.scaleZ = scaleZ;
    }
}