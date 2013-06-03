package trussoptimizater.Gui.Element2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import trussoptimizater.Truss.Elements.Bar;

public class LocalBarAxis2D extends ArrowElement2D{

    public static final int AXIS_LENGTH = 18;
    public static final Color X_AXIS_ARROW_COLOR = Color.GREEN;
    public static final Color Z_AXIS_ARROW_COLOR = Color.BLUE;
    public static final BasicStroke stroke = new BasicStroke(2);

    private Bar bar;

    public LocalBarAxis2D(Bar bar){
        super(bar);
        this.bar = bar;
    }

    public Shape getLocalXAxis2D(){
        Point2D.Double startPoint = bar.getMidPoint();
        Point2D.Double transformedStartPoint = new Point2D.Double();
        AffineTransform at = new AffineTransform();
        at.translate(AXIS_LENGTH*Math.cos(bar.getAngle()), AXIS_LENGTH*Math.sin(bar.getAngle()));
        at.transform(startPoint, transformedStartPoint);
        return super.createArrow(bar.getAngle(), transformedStartPoint ,AXIS_LENGTH , ARROW_HEAD_HEIGHT);
    }

    public Shape getLocalZAxis2D(){
        Point2D.Double startPoint = bar.getMidPoint();
        Point2D.Double transformedStartPoint = new Point2D.Double();
        AffineTransform at = new AffineTransform();
        at.translate(AXIS_LENGTH*Math.sin(bar.getAngle()), -AXIS_LENGTH*Math.cos(bar.getAngle()));
        at.transform(startPoint, transformedStartPoint);
        return  super.createArrow(bar.getAngle()- Math.toRadians(90), transformedStartPoint ,AXIS_LENGTH , ARROW_HEAD_HEIGHT);
    }


}
