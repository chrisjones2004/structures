package trussoptimizater.Gui.Element2D;

import java.awt.Font;
import java.awt.Shape;
import trussoptimizater.Truss.Elements.Element;


/**
 *
 * @author Chris
 */
public  abstract class Element2D { 

    protected Shape shape;
    protected Shape highLightShape;
    public static final int HIGHLIGHT_TOLERANCE = 10;
    public static final Font font = new Font(Font.DIALOG, Font.PLAIN, 8);
    protected Element element;

    
    public Element2D(){
        this(null);
    }
    
    public Element2D(Element element){
        this.element = element;
    }

    public abstract Shape getHighLightShape();
    public abstract Shape getShape();


   
    /*public static Stroke getStroke() {
        return stroke;
    }

    public void setStroke(BasicStroke stroke) {
        this.stroke = stroke;
    }*/
    
    public void setHighLightShape(Shape highLightShape) {
        this.highLightShape = highLightShape;
    }
    

    

    

}
