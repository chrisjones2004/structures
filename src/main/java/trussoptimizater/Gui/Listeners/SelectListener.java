package trussoptimizater.Gui.Listeners;

import trussoptimizater.Truss.TrussModel;
import java.awt.event.*;
import java.awt.*;
import javax.swing.SwingUtilities;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import trussoptimizater.Gui.Element2D.*;
import trussoptimizater.Gui.GUI;
import trussoptimizater.Gui.GUIModels.GUIModeModel;
import trussoptimizater.Truss.Elements.*;
import trussoptimizater.Truss.Materials.Steel;

public class SelectListener implements MouseMotionListener, MouseListener {

    private TrussModel truss;
    private GUI gui;

    public SelectListener(GUI gui) {
        this.gui = gui;
        this.truss = gui.getTruss();
    }

    public void mouseEntered(MouseEvent e) {
        gui.getView().setCursor();
    }

    public void mouseExited(MouseEvent e) {
        gui.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void mouseMoved(MouseEvent e) {
        //Highlight beams, nodes if mouse moves over them
        //also display bar and node numbers

        Bar2D bar2D;
        Bar bar;
        for (int i = 0; i < truss.getBarModel().size(); i++) {
            bar = truss.getBarModel().get(i);
            bar2D = new Bar2D(bar);
            checkHighLight(e, bar, bar2D);
        }

        Node node;
        Node2D node2D;
        for (int i = 0; i < truss.getNodeModel().size(); i++) {
            node = truss.getNodeModel().get(i);
            node2D = new Node2D(node);
            checkHighLight(e, node, node2D);
        }
        Load load;
        Load2D load2D;
        double maxLoad = truss.getMaxABSLoad();
        for (int i = 0; i < truss.getLoadModel().size(); i++) {
            load = truss.getLoadModel().get(i);
            load2D = new Load2D(load,maxLoad);
            checkHighLight(e, load, load2D);
        }

        Support support;
        Support2D support2D;
        for (int i = 0; i < truss.getSupportModel().size(); i++) {
            support = truss.getSupportModel().get(i);
            support2D = new Support2D(support);
            checkHighLight(e, support, support2D);
        }


    }

    public void checkHighLight(MouseEvent e, Element E, Element2D E2D) {
        Point2D p = gui.getView().getZoomAndPanListener().getMouseLocation();

        if (E2D.getHighLightShape().contains(p)) {
            E.setHighLighted(true);
        } else if (E.isHighLighted()) {
            E.setHighLighted(false);
        }
    }


    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if (gui.getView().getPopupListener().maybeShowPopup(e)) {
            return;
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            Point2D.Double p = gui.getView().getZoomAndPanListener().getMouseLocation();
            gui.getView().getViewModel().setDragStartScreen(e.getPoint());
        }
        if (gui.getGuiModeModel().getMode() == GUIModeModel.SELECT_MODE && SwingUtilities.isLeftMouseButton(e)) {
            gui.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            gui.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

    }

    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            gui.getView().getViewModel().setCurrentlyDragSelecting(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        gui.getView().requestFocus();
        if (gui.getView().getPopupListener().maybeShowPopup(e)) {
            return;
        }
        gui.getView().setCursor();
        selectCheck(e);
        gui.getView().getViewModel().setCurrentlyDragSelecting(false);
        gui.getView().getViewModel().setSelectionRectangle(new Rectangle2D.Double());

        //Node Mode
        if (gui.getGuiModeModel().getMode() == GUIModeModel.NODE_MODE && SwingUtilities.isLeftMouseButton(e)) {
            truss.getNodeModel().add(new Node(truss.getNodeModel().size() + 1,(int) gui.getView().getZoomAndPanListener().getMouseLocation().x, (int) gui.getView().getZoomAndPanListener().getMouseLocation().y));
            return;
        }

        //Bar Mode
        if (gui.getGuiModeModel().getMode() == GUIModeModel.BAR_MODE) {
            mouseReleasedBarMode(e);
        }
    }

    public void checkClickSelect(MouseEvent e, Element E, Element2D E2D) {
       Point2D p = gui.getView().getZoomAndPanListener().getMouseLocation();

        if (E2D.getHighLightShape().contains(p)) {
            E.setSelected(true);
        } else if (!E2D.getHighLightShape().contains(p) && e.isControlDown() && E.isSelected()) {
            E.setSelected(true);
        } else if (!E2D.getHighLightShape().contains(p) && !e.isControlDown() && !SwingUtilities.isMiddleMouseButton(e)) {
            E.setSelected(false);
        }
    }


    public void checkRightDragSelect(MouseEvent e, Element E, Element2D E2D) {
        Rectangle2D transformedRect = gui.getView().getZoomAndPanListener().transformRectangle(gui.getView().getViewModel().getSelectionRectangle());
        //transformedRect.i
        if (transformedRect .contains((Rectangle2D) E2D.getHighLightShape().getBounds2D())) {
            E.setSelected(true);
        } else if (!transformedRect .contains((Rectangle2D) E2D.getHighLightShape().getBounds2D()) && e.isControlDown() && E.isSelected()) {
            E.setSelected(true);
        } else if (!transformedRect .contains((Rectangle2D) E2D.getHighLightShape().getBounds2D()) && !e.isControlDown() && !SwingUtilities.isMiddleMouseButton(e) && E.isSelected()) {
            E.setSelected(false);
        }
    }

    public void checkLeftDragSelect(MouseEvent e, Element E, Element2D E2D) {
        Rectangle2D transformedRect = gui.getView().getZoomAndPanListener().transformRectangle(gui.getView().getViewModel().getSelectionRectangle());

        if (E2D.getHighLightShape().intersects(transformedRect)) {
            E.setSelected(true);
        } else if (!E2D.getHighLightShape().intersects(transformedRect) && e.isControlDown() && E.isSelected()) {
            E.setSelected(true);
        } else if (!E2D.getHighLightShape().intersects(transformedRect) && !e.isControlDown() && !SwingUtilities.isMiddleMouseButton(e) && E.isSelected()) {
            E.setSelected(false);
        }
    }
    /*MOUSE METHODS FOR ACTIONS*/

    public void selectCheck(MouseEvent e) {

        if (gui.getGuiModeModel().getMode() != GUIModeModel.SELECT_MODE) {
            return;
        }
        Node node;
        Node2D node2D;
        for (int i = 0; i < truss.getNodeModel().size(); i++) {
            node = truss.getNodeModel().get(i);
            node2D = new Node2D(node);

            
            if (gui.getView().getViewModel().isCurrentlyDragSelecting()) {
                if(gui.getView().getViewModel().isDraggingRight()){
                    checkRightDragSelect(e, node, node2D);
                }
                if(gui.getView().getViewModel().isDraggingLeft()){
                    checkLeftDragSelect(e, node, node2D);
                }
            }else{
                checkClickSelect(e, node, node2D);
            }
        }
        Bar bar;
        Bar2D bar2D;
        for (int i = 0; i < truss.getBarModel().size(); i++) {
            bar = truss.getBarModel().get(i);
            bar2D = new Bar2D(bar);
            
            if (gui.getView().getViewModel().isCurrentlyDragSelecting()) {
                 if(gui.getView().getViewModel().isDraggingRight()){
                    checkRightDragSelect(e, bar, bar2D);
                }
                if(gui.getView().getViewModel().isDraggingLeft()){
                    checkLeftDragSelect(e, bar, bar2D);
                }
            }else{
                checkClickSelect(e, bar, bar2D);
            }
        }

        Support support;
        Support2D support2D;
        for (int i = 0; i < truss.getSupportModel().size(); i++) {
            support = truss.getSupportModel().get(i);
            support2D = new Support2D(support);
            
            if (gui.getView().getViewModel().isCurrentlyDragSelecting()) {
                if(gui.getView().getViewModel().isDraggingRight()){
                    checkRightDragSelect(e, support, support2D);
                }
                if(gui.getView().getViewModel().isDraggingLeft()){
                    checkLeftDragSelect(e, support, support2D);
                }
            }else{
                checkClickSelect(e, support, support2D);
            }
        }

        Load load;
        Load2D load2D;
        double maxLoad = truss.getMaxABSLoad();
        for (int i = 0; i < truss.getLoadModel().size(); i++) {
            load = truss.getLoadModel().get(i);
            load2D = new Load2D(load, maxLoad);
            
            if (gui.getView().getViewModel().isCurrentlyDragSelecting()) {
                if(gui.getView().getViewModel().isDraggingRight()){
                    checkRightDragSelect(e, load, load2D);
                }
                if(gui.getView().getViewModel().isDraggingLeft()){
                    checkLeftDragSelect(e, load, load2D);
                }
                
            }else{
                checkClickSelect(e, load, load2D);
            }
        }

    }

    public void mouseReleasedBarMode(MouseEvent e) {
        //If in drawing mode and left click to finish line
        if (gui.getView().getViewModel().isCurrentlyDrawingBar() && SwingUtilities.isLeftMouseButton(e)) {
            for (int i = 0; i < truss.getNodeModel().size(); i++) {
                Rectangle rectangle = new Rectangle((int) gui.getView().getZoomAndPanListener().getMouseLocation().getX() - Element2D.HIGHLIGHT_TOLERANCE / 2, (int) gui.getView().getZoomAndPanListener().getMouseLocation().getY() - Element2D.HIGHLIGHT_TOLERANCE / 2, Element2D.HIGHLIGHT_TOLERANCE, Element2D.HIGHLIGHT_TOLERANCE);
                if (rectangle.contains(truss.getNodeModel().get(i).getPoint())) {

                    truss.getBarModel().add(new Bar(truss.getBarModel().size() + 1 ,gui.getView().getViewModel().getBarStartNode(), truss.getNodeModel().get(i),  truss.getMaterials().get(Steel.MATERIAL_NAME)));
                    gui.getView().getViewModel().setCurrentlyDrawingBar(false);
                    return;
                }
            }
            return;

        }

        //In bar mode but not drawing
        //Check to see if click is on node --> start bar
        for (int i = 0; i < truss.getNodeModel().size(); i++) {
            Rectangle rectangle = new Rectangle((int) gui.getView().getZoomAndPanListener().getMouseLocation().x - Element2D.HIGHLIGHT_TOLERANCE / 2, (int) gui.getView().getZoomAndPanListener().getMouseLocation().y - Element2D.HIGHLIGHT_TOLERANCE / 2, Element2D.HIGHLIGHT_TOLERANCE, Element2D.HIGHLIGHT_TOLERANCE);
            if (rectangle.contains(truss.getNodeModel().get(i).getPoint()) && SwingUtilities.isLeftMouseButton(e)) {
                gui.getView().getViewModel().setBarStartNode(truss.getNodeModel().get(i));
                gui.getView().getViewModel().setCurrentlyDrawingBar(true);
                return;

            }
        }

        //Right click to cancel line draw
        if (gui.getView().getViewModel().isCurrentlyDrawingBar() && SwingUtilities.isRightMouseButton(e)) {
            gui.getView().getViewModel().setCurrentlyDrawingBar(false);
            return;
        }
    }
}
