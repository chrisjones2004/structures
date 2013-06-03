package trussoptimizater.Gui.Actions;

import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Point2D;
import javax.swing.*;

import trussoptimizater.Gui.*;

public class ZoomOutAction extends AbstractAction {

    private GUI gui;

    public ZoomOutAction(String text, String desc, GUI gui) {
        this(text,null,desc,gui);
    }

    public ZoomOutAction(String text, ImageIcon icon, String desc, GUI gui) {
        this(text,icon,desc,null,gui);

    }

    public ZoomOutAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui) {
        super(text, icon);
        this.gui= gui;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public void actionPerformed(ActionEvent e) {
        //Point2D.Double centerPoint = new Point2D.Double(View.viewDimension.getWidth() / 2, View.viewDimension.getHeight() / 2);
        //gui.getView().getZoomAndPanListener().zoomCamera(new Point((int) centerPoint.x, (int) centerPoint.y), -1);
        Point2D.Double centerPoint = new Point2D.Double(View.viewDimension.getWidth() / 2, View.viewDimension.getHeight() / 2);
        gui.getView().getZoomAndPanListener().zoomCamera(new Point((int) centerPoint.x, (int) centerPoint.y), 1);


    }
}