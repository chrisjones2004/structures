/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trussoptimizater.Gui.Element2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import trussoptimizater.Truss.Elements.Load;


public class Load2D extends ArrowElement2D {

    public static final BasicStroke stroke = new BasicStroke(1);
    public static final Color COLOR = Color.BLUE;
    public static final Color HIGHLIGHT_COLOR = Color.YELLOW;
    public static final int MAX_LOAD_ARROW_HEIGHT = 50; //MAX_ARROW_HEIGHT

    private Load load;
    private double maxLoad;

    public Load2D(Load load, double maxLoad) {
        super(load);
        this.load = load;
        this.maxLoad = maxLoad;
    }

    @Override
    public Shape getShape() {
        int arrowLength = (int)((load.getLoad() / maxLoad) * MAX_LOAD_ARROW_HEIGHT);

        if (load.getOrientation().equals(Load.VERTICAL_LOAD)) {
            return  createArrow(Math.toRadians(90), load.getNode().getPoint2D(), arrowLength, ARROW_HEAD_HEIGHT);
        } else if (load.getOrientation().equals(Load.HORIZOANTAL_LOAD)) {
            return  createArrow(Math.toRadians(0), load.getNode().getPoint2D(), arrowLength, ARROW_HEAD_HEIGHT);
        }
        return null;
    }


    @Override
    public Shape getHighLightShape() {
        //Load.getMaxABSLoad()
        AffineTransform at = new AffineTransform();
        double arrowLength = Math.abs(load.getLoad()/maxLoad) * MAX_LOAD_ARROW_HEIGHT;
        Rectangle2D rect = new Rectangle2D.Double(load.getNode().x, load.getNode().z - Element2D.HIGHLIGHT_TOLERANCE / 2,arrowLength , Element2D.HIGHLIGHT_TOLERANCE);
        
        if(load.getOrientation().equals(Load.HORIZOANTAL_LOAD) && load.getLoad() >0){
            at.rotate(Math.toRadians(180), load.getNode().x, load.getNode().z);
        }else if(load.getOrientation().equals(Load.HORIZOANTAL_LOAD) && load.getLoad() <0){
            at.rotate(Math.toRadians(0), load.getNode().x, load.getNode().z);
        }else if(load.getOrientation().equals(Load.VERTICAL_LOAD) && load.getLoad() >0){
            at.rotate(Math.toRadians(270), load.getNode().x, load.getNode().z);
        }else if(load.getOrientation().equals(Load.VERTICAL_LOAD) && load.getLoad() <0){
            at.rotate(Math.toRadians(90), load.getNode().x, load.getNode().z);
        }
        return at.createTransformedShape(rect);
    }
}
