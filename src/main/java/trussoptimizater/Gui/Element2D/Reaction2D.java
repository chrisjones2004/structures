package trussoptimizater.Gui.Element2D;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import trussoptimizater.Truss.Elements.Support;

public class Reaction2D extends ArrowElement2D{
    
    public static final Color COLOR = Color.BLUE;
    public static final BasicStroke stroke = new BasicStroke(2);
    public static final int REACTION_ARROW_LENGTH = 30;
    protected Support support;
    
    public Reaction2D(Support support){
        super(support);
        this.support = support;  
    }

    public Shape getReactionXShape(){
        if(support.getReactionX()>0){
            return super.createArrow(Math.toRadians(0), support.getNode().getPoint2D(), REACTION_ARROW_LENGTH, ARROW_HEAD_HEIGHT );
        }else{
            return super.createArrow(Math.toRadians(180), support.getNode().getPoint2D(), REACTION_ARROW_LENGTH, ARROW_HEAD_HEIGHT );
        }
        
    }

    public Shape getReactionZShape(){
        if(support.getReactionZ()>0){
            return super.createArrow(Math.toRadians(-270), support.getNode().getPoint2D(), REACTION_ARROW_LENGTH, ARROW_HEAD_HEIGHT );
        }else{
            return super.createArrow(Math.toRadians(-90), support.getNode().getPoint2D(), REACTION_ARROW_LENGTH, ARROW_HEAD_HEIGHT );
        }
        
    }

}
