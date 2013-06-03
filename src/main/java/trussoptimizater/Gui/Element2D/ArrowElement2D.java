package trussoptimizater.Gui.Element2D;

import java.awt.Shape;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import trussoptimizater.Truss.Elements.Element;


abstract public class ArrowElement2D extends Element2D {

    public static final int ARROW_HEAD_HEIGHT = 4;

    
    public Point2D.Double arrowTailPoint;
    public Point2D.Double arrowMidPoint;
    

    public ArrowElement2D(Element element) {
        super(element);
    }



    /**
     *
     * @param angle in radians where clockwise is postive and 3 0 'clock is 0 radians
     * @param headPoint in pixels
     * @param arrowLength in pixels
     * @param headHeight in pixels
     * @return Shape 
     */
    protected Shape createArrow(double angle, Point2D.Double headPoint, int arrowLength,  int headHeight) {

        Point2D.Double tailPoint = new Point2D.Double(headPoint.x - arrowLength, headPoint.y);
        Point2D.Double middlePoint = new Point2D.Double();
        this.setArrowTailPoint(tailPoint);



        Path2D arrow = new Path2D.Double();
        arrow.moveTo(headPoint.x, headPoint.y);
        arrow.lineTo(tailPoint.x, tailPoint.y);


        if (tailPoint.x < headPoint.x) {
            arrow.lineTo(headPoint.x, headPoint.y);
            arrow.lineTo(headPoint.x - headHeight, headPoint.y - headHeight);
            arrow.lineTo(headPoint.x, headPoint.y);
            arrow.lineTo(headPoint.x - headHeight, headPoint.y + headHeight);
        } else {
            arrow.lineTo(headPoint.x, headPoint.y);
            arrow.lineTo(headPoint.x + headHeight, headPoint.y - headHeight);
            arrow.lineTo(headPoint.x, headPoint.y);
            arrow.lineTo(headPoint.x + headHeight, headPoint.y + headHeight);
        }

        AffineTransform at = new AffineTransform();
        at.rotate(angle, headPoint.x, headPoint.y);

        Point2D.Double transformedTailPoint = new Point2D.Double();
        Point2D.Double transformedMiddlePoint = new Point2D.Double();


        at.transform( middlePoint , transformedMiddlePoint);
        at.transform( tailPoint ,transformedTailPoint);
        this.setArrowMidPoint(transformedMiddlePoint);
        this.setArrowTailPoint(transformedTailPoint);

        return at.createTransformedShape(arrow);

    }


    @Override
    public Shape getHighLightShape() {
        return null;
    }

    @Override
    public Shape getShape() {
        return null;
    }



    public Point2D.Double getArrowTailPoint() {
        return arrowTailPoint;
    }

    public Double getArrowMidPoint() {
        return arrowMidPoint;
    }

    public void setArrowMidPoint(Double arrowMidPoint) {
        this.arrowMidPoint = arrowMidPoint;
    }

   public void setArrowTailPoint(Point2D.Double arrowStartPoint) {
        this.arrowTailPoint = arrowStartPoint;
    }


}
