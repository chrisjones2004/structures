package trussoptimizater.Gui.Element2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import trussoptimizater.Truss.Elements.Bar;

/**
 *
 * @author Chris
 */
public class Bar2D extends Element2D {

    /*Colors*/
    public static final Color COLOR = Color.BLACK;
    public static final Color HIGHLIGHT_COLOR = Color.RED;
    public static final Color TENSION_COLOR = Color.MAGENTA;
    public static final Color COMPRESSION_COLOR = Color.CYAN;
    public static final Color DEFLECTED_COLOR = Color.PINK;
    public static final Color BENDING_MOMENT_COLOR = Color.YELLOW;
    public static final Color SHEAR_FORCE_COLOR = Color.ORANGE;
    public static final BasicStroke CENTERLINE_STROKE = new BasicStroke(2);
    public static final BasicStroke BOUNDS_STROKE = new BasicStroke(1);
    private Bar bar;

    public Bar2D(Bar bar) {
        super(bar);
        this.bar = bar;
    }

    @Override
    public Shape getHighLightShape() {
        AffineTransform at = new AffineTransform();
        Rectangle2D rect = new Rectangle2D.Double(bar.getNode1().x, bar.getNode1().z - Element2D.HIGHLIGHT_TOLERANCE / 2, bar.getLength(), Element2D.HIGHLIGHT_TOLERANCE);
        at.rotate(bar.getAngle(), bar.getNode1().x, bar.getNode1().z);
        return at.createTransformedShape(rect);
    }

    @Override
    public Shape getShape() {
        return new Line2D.Double(bar.getNode1().x, bar.getNode1().z, bar.getNode2().x, bar.getNode2().z);
    }

    public Shape getDeflectedShape(double zoomScale) {
        zoomScale = zoomScale/100;
        Path2D deflectedBarPath = new Path2D.Double();
        Point2D[] deflectedPoints = bar.getBarDeflectedPoints(zoomScale);
        /*if(deflectedPoints == null){
            return new Line2D.Double(   bar.getNode1().getDeflectedPoint(zoomScale).x,
                                        bar.getNode1().getDeflectedPoint(zoomScale).y,
                                        bar.getNode2().getDeflectedPoint(zoomScale).x,
                                        bar.getNode2().getDeflectedPoint(zoomScale).y);
        }*/

        for (int j = 0; j < deflectedPoints.length; j++) {
            if(j==0){
                deflectedBarPath.moveTo(deflectedPoints[j].getX(), deflectedPoints[j].getY());
            }else{
                deflectedBarPath.lineTo(deflectedPoints[j].getX(), deflectedPoints[j].getY());
            }

        }
        return deflectedBarPath;
    }
}
