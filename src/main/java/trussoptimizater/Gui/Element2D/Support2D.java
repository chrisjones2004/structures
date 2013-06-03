package trussoptimizater.Gui.Element2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import trussoptimizater.Truss.Elements.Support;


public class Support2D extends Element2D{

    public static final Color FILL_COLOR = Color.BLUE;
    public static final Color BORDER_COLOR = new Color(153, 153, 255);
    public static final Color HIGHLIGHT_COLOR = Color.RED;
    public  static final BasicStroke stroke = new BasicStroke(1);
    
    public static final int SUPPORT_WIDTH = 10;
    
    private Support support;
    
    public Support2D(Support support){
        super(support);
        this.support = support;
    }

    @Override
    public Shape getHighLightShape() {
        return new Rectangle2D.Double(support.getNode().x - Element2D.HIGHLIGHT_TOLERANCE , support.getNode().z - Element2D.HIGHLIGHT_TOLERANCE/4, Support2D.SUPPORT_WIDTH + Element2D.HIGHLIGHT_TOLERANCE, Support2D.SUPPORT_WIDTH + Element2D.HIGHLIGHT_TOLERANCE);
    }

    @Override
    public Shape getShape() {
        return new Rectangle2D.Double(support.getNode().x - Support2D.SUPPORT_WIDTH/2 , support.getNode().z  + Node2D.NODE_DIAMETER/2 , Support2D.SUPPORT_WIDTH , Support2D.SUPPORT_WIDTH );
    }
}
