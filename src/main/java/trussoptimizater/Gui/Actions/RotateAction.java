package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import javax.swing.*;

import trussoptimizater.*;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Truss.Elements.Node;

public class RotateAction extends AbstractAction {

    private GUI gui;
    private TrussModel truss;
    private Node nodeAnchor;
    private double angle;

   ///*
    public RotateAction(GUI gui, TrussModel truss) {
        this(null, null, null, gui, truss);
    }

    public RotateAction(String text, String desc, GUI gui, TrussModel truss) {
        this(text, null, desc, gui, truss);
    }

    public RotateAction(String text, ImageIcon icon, String desc, GUI gui, TrussModel truss) {
        this(text, icon, desc, null, gui, truss);

    }//*/

    public RotateAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui, TrussModel truss) {
        super(text, icon);
        this.gui = gui;
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        //rotateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
    }

    public void actionPerformed(ActionEvent e) {
        gui.getRotateDialog().getDialog().setLocationRelativeTo(gui.getFrame());
        gui.getRotateDialog().getDialog().setVisible(true);
        //gui.getMyGlassPane().setDrawCursor(false);
        //rotate();
    }

    public void rotate() {
        AffineTransform at = new AffineTransform();
        at.rotate(angle, nodeAnchor.x, nodeAnchor.z);
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
    public void setAngle(double angle) {
        this.angle = angle;
    }
}