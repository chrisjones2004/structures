package trussoptimizater.Gui.Actions;

import trussoptimizater.Truss.TrussModel;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import trussoptimizater.Gui.*;
import trussoptimizater.Gui.Element2D.Node2D;

public class ZoomAllAction extends AbstractAction {

    private GUI gui;
    private TrussModel truss;
    public static final int BORDER_THICKNESS = 20;

    public ZoomAllAction(String text, String desc, GUI gui, TrussModel truss) {
        this(text,null,desc,gui,truss);
    }

    public ZoomAllAction(String text, ImageIcon icon, String desc, GUI gui, TrussModel truss) {
        this(text,icon,desc,null,gui,truss);

    }

    public ZoomAllAction(String text, ImageIcon icon, String desc, Integer mnemonic, GUI gui, TrussModel truss) {
        super(text, icon);
        this.gui= gui;
        this.truss = truss;
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public void actionPerformed(ActionEvent e) {
        Rectangle2D nodeBounds = getNodeBounds();
        Point2D.Double centerPoint = new Point2D.Double(gui.getView().getG2d().getClipBounds().getCenterX(), gui.getView().getG2d().getClipBounds().getCenterY());
        gui.getView().getZoomAndPanListener().moveCamera(new Point2D.Double(nodeBounds.getCenterX(), nodeBounds.getCenterY()), centerPoint);
        centerPoint = new Point2D.Double(View.viewDimension.getWidth() / 2, View.viewDimension.getHeight() / 2);
        if (gui.getView().getZoomAndPanListener().transformRectangle(gui.getView().getBounds()).contains(nodeBounds)) {
            //Zoom out until nodes are focused
            while (gui.getView().getZoomAndPanListener().transformRectangle(gui.getView().getBounds()).contains(nodeBounds)) {
                gui.getView().getZoomAndPanListener().zoomCamera(new Point((int) centerPoint.x, (int) centerPoint.y), -1);
            }
            gui.getView().getZoomAndPanListener().zoomCamera(new Point((int) centerPoint.x, (int) centerPoint.y), 1);
        } else {
            //Zoom in until nodes are visible
            while (!gui.getView().getZoomAndPanListener().transformRectangle(gui.getView().getBounds()).contains(nodeBounds)) {
                gui.getView().getZoomAndPanListener().zoomCamera(new Point((int) centerPoint.x, (int) centerPoint.y), 1);
            }
            //gui.getView().getZoomAndPanListener().zoomCamera(new Point((int) centerPoint.x, (int) centerPoint.y), -1);
        }
    }
    
    
    public Rectangle2D getNodeBounds(){
        
        if(truss.getNodeModel().size()==1){
            Node2D node2D = new Node2D(truss.getNodeModel().get(0));
            return node2D.getHighLightShape().getBounds();
        }
        int maxLeft = Integer.MAX_VALUE;
        int maxTop = Integer.MAX_VALUE;
        int maxBottom = Integer.MIN_VALUE;
        int maxRight = Integer.MIN_VALUE;
        for(int i=0;i<truss.getNodeModel().size();i++){
            if(truss.getNodeModel().get(i).getPoint().x<maxLeft){
                maxLeft = truss.getNodeModel().get(i).getPoint().x;
            }
            if(truss.getNodeModel().get(i).getPoint().x>maxRight){
                maxRight = truss.getNodeModel().get(i).getPoint().x;
            }
            if(truss.getNodeModel().get(i).getPoint().y<maxTop){
                maxTop = truss.getNodeModel().get(i).getPoint().y;
            }
            if(truss.getNodeModel().get(i).getPoint().y>maxBottom){
                maxBottom = truss.getNodeModel().get(i).getPoint().y;
            }
        }
        return new Rectangle2D.Double(maxLeft-ZoomAllAction.BORDER_THICKNESS,maxTop-ZoomAllAction.BORDER_THICKNESS,maxRight-maxLeft+2*ZoomAllAction.BORDER_THICKNESS,maxBottom-maxTop+2*ZoomAllAction.BORDER_THICKNESS);
    }
}