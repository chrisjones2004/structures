/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trussoptimizater.Gui.Element2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import trussoptimizater.Truss.Elements.Node;


public class Node2D extends Element2D{
    
    
    public static final Color COLOR = Color.RED;
    public static final Color HIGHLIGHT_COLOR = Color.GREEN;
    public static final Color DEFLECTED_COLOR = Color.DARK_GRAY;
    public static final int NODE_DIAMETER = 6;
    public static final BasicStroke stroke = new BasicStroke(1);
    
    private Node node;
    
    public Node2D(Node node){
        super(node);
        this.node = node;
    }
    
    public Shape getHighLightShape() {
        return new Ellipse2D.Double(node.x - Element2D.HIGHLIGHT_TOLERANCE / 2, node.z - Element2D.HIGHLIGHT_TOLERANCE / 2, Element2D.HIGHLIGHT_TOLERANCE, Element2D.HIGHLIGHT_TOLERANCE);
    }

    @Override
    public Shape getShape() {
        return new Ellipse2D.Double(node.x - Node2D.NODE_DIAMETER / 2, node.z - Node2D.NODE_DIAMETER / 2, Node2D.NODE_DIAMETER, Node2D.NODE_DIAMETER);
    }

    public Shape getDeflectedShape(double zoomScale) {
        zoomScale = zoomScale/100;
        return new Ellipse2D.Double( node.getDeflectedPoint(zoomScale).x - NODE_DIAMETER / 2, node.getDeflectedPoint(zoomScale).y - NODE_DIAMETER / 2, NODE_DIAMETER, NODE_DIAMETER);
  }


}
