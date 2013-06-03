package trussoptimizater.Gui;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.Toolkit.*;
import java.text.*;


//imporvements
//can not see x axis title if y axis is in middle of panel, could use moveable titles like excell
//does not round numbers yet



public class  FitnessGraph extends JPanel {
    //Strokes used for paiting in graph
    private final Stroke axisStroke = new BasicStroke(2);
    //Stoke used for painting labels
    private final Stroke labelsStroke = new BasicStroke(1);
    private final Stroke cordsStroke = new BasicStroke(1);
    private final Stroke borderStroke = new BasicStroke(1);
    private final Stroke lineGraphStroke = new BasicStroke(1);
    private final Stroke markerStroke = new BasicStroke(1);

    //gridline stroke
    float[] dash = {3.0f};
    Stroke gridLineStroke = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,1.0f,dash,0.0f);

    //ArrayLists of points
    private ArrayList<Point2D> points = new ArrayList<Point2D>();

    //Graph titles
    private String graphTitle,XAxisTitle,YAxisTitle;


    public static final int BORDER_HEIGHT_INSET = 70;
    public static final int BORDER_WIDTH_INSET = 70;

    //maximum values in data
    private double min_Xcord,min_Ycord,max_Xcord,max_Ycord = 0;

    private double graphicalXaxisLength,graphicalYaxisLength;


    private double yAxisXPostion,xAxisYPostion = 0;

    //No of numeric markers to indicate graph values
    private final int noXMarkers = 10;
    private final int noYMarkers = 10;
    private double xMarkerSpacing;
    private double yMarkerSpacing;

    //Range between max and min values in data
    private double xRange,yRange;

    //current graph types
    private int graphType;
    public static final int LINE_GRAPH_TYPE    = 0;
    public static final int SCATTER_GRAPH_TYPE = 1;

    //marker shapes
    private int pointShape;
    private final double pointDiameter =8;
    public static final int STAR_POINT_SHAPE    = 0;
    public static final int CIRCLE_POINT_SHAPE = 1;

    //gridLines
    private boolean minorXaxisGridLines = false;
    private boolean minorYaxisGridLines = false;
    private boolean majorXaxisGridLines = false;
    private boolean majorYaxisGridLines = false;

    //axis
    private boolean fixedXAxis = false;
    private boolean fixedYAxis = false;


    //Labels
    private JLabel YAxisLabel;
    private JLabel graphTitleLabel;
    private JLabel XAxisLabel;

    public static final DecimalFormat DF = new DecimalFormat("#");


    public FitnessGraph(String gt, String xt, String yt) {
        this(new ArrayList<Point2D>(), gt, xt, yt);
    }

    public FitnessGraph(ArrayList<Point2D> points, String gt, String xt, String yt) {
        super();
        this.XAxisTitle = xt;
        this.YAxisTitle = yt;
        this.graphTitle = gt;
        this.graphType = FitnessGraph.LINE_GRAPH_TYPE;
        this.points = points;
        initLabels();
        initGraphStats();
    }

    public void initLabels(){
        YAxisLabel = new JLabel(YAxisTitle);
        YAxisLabel.setOpaque(false);
        YAxisLabel.setForeground(Color.WHITE);

        graphTitleLabel = new JLabel(graphTitle);
        graphTitleLabel.setOpaque(false);
        graphTitleLabel.setForeground(Color.WHITE);
        graphTitleLabel.setBackground(Color.BLACK);

        XAxisLabel = new JLabel(XAxisTitle);
        XAxisLabel.setOpaque(false);
        XAxisLabel.setForeground(Color.WHITE);
    }



    private double findMaxNumber(ArrayList<Double> nos){
        double maxValue = 0;
        for(int i = 0;i<nos.size();i++){
            if(nos.get(i)>maxValue){
                maxValue = nos.get(i);
            }
        }//end of for
        return maxValue;
    }

    private double findMinNumber(ArrayList<Double> nos){
        double minValue = 0;
        for(int i = 0;i<nos.size();i++){
            if(nos.get(i)<minValue){
                minValue = nos.get(i);
            }
        }//end of for
        return minValue;
    }

    private ArrayList<Double> getXCords(ArrayList<Point2D> cords){
        ArrayList<Double> xCords = new ArrayList<Double>(cords.size());
        for(int i = 0;i<cords.size();i++){
            xCords.add(cords.get(i).getX());
        }
        return xCords;
    }

    private ArrayList<Double> getYCords(ArrayList<Point2D> cords){
        ArrayList<Double> yCords = new ArrayList<Double>(cords.size());
        for(int i = 0;i<cords.size();i++){
            yCords.add(cords.get(i).getY());
        }
        return yCords;
    }


    private void paintAxis(Graphics2D g2d){
        //painting x axis
        g2d.setColor(Color.WHITE);
        g2d.setStroke(axisStroke);
        Line2D xAxis = new Line2D.Double(BORDER_WIDTH_INSET, xAxisYPostion, this.getWidth()-BORDER_WIDTH_INSET, xAxisYPostion);
        g2d.draw(xAxis);
        //painting y axis
        g2d.setColor(Color.WHITE);
        g2d.setStroke(axisStroke);
        Line2D yAxis = new Line2D.Double(yAxisXPostion, BORDER_HEIGHT_INSET, yAxisXPostion, this.getHeight()-BORDER_HEIGHT_INSET);
        g2d.draw(yAxis);
    }

    private void paintLabels(Graphics2D g2d){
        //drawign graph/axis titles
        g2d.setColor(Color.cyan);
        g2d.setStroke(labelsStroke);
        g2d.rotate(-90*Math.PI/180);
        //int charHeight =15;
        //int charWidth = 8;
        //String mavValueToString = Double.toString(max_Ycord);
        

        int stringWidth = g2d.getFontMetrics().stringWidth(DF.format(max_Ycord));
        int stringHeight = g2d.getFontMetrics().getHeight();
        g2d.drawString(YAxisTitle, -this.getHeight()/2,(int)yAxisXPostion-stringWidth);
        g2d.rotate(90*Math.PI/180);
        g2d.drawString(XAxisTitle, this.getWidth()/2 +5, (int)xAxisYPostion + 2*stringHeight );

        stringWidth = g2d.getFontMetrics().stringWidth(graphTitle);

        g2d.drawString(graphTitle, this.getWidth()/2 -stringWidth/2, (int)BORDER_HEIGHT_INSET );
    }

    private void paintMarkers(Graphics2D g2d){

        //drawing interval markers for y axis
        g2d.setColor(Color.WHITE);
        g2d.setStroke(markerStroke);

        double yInterval = yRange/noYMarkers;
        int stringWidth = 0;
        int markerLength = 9;
        Line2D marker;


        for(int i = 0;i<=noYMarkers;i++){
            marker = new Line2D.Double(yAxisXPostion-markerLength/2,BORDER_HEIGHT_INSET + i*yMarkerSpacing,yAxisXPostion+markerLength/2,BORDER_HEIGHT_INSET+ i*yMarkerSpacing);
            g2d.draw(marker);
            double markerValue = yInterval*i +min_Ycord;
            String value = DF.format(markerValue);
            stringWidth  = g2d.getFontMetrics().stringWidth(value);
            g2d.drawString(value, (int)yAxisXPostion - stringWidth, this.getHeight()-(int)(BORDER_HEIGHT_INSET + i*yMarkerSpacing));
        }

        //drawing interval markers for x axis
        g2d.setColor(Color.WHITE);
        g2d.setStroke(markerStroke);

        double xInterval = xRange/noXMarkers ;
        markerLength = 9;
        int stringHeight = 0;
        for(int i = 0;i<=noXMarkers;i++){
            marker = new Line2D.Double(BORDER_WIDTH_INSET + i*xMarkerSpacing,xAxisYPostion-markerLength/2,BORDER_WIDTH_INSET + i*xMarkerSpacing,xAxisYPostion+markerLength/2);
            g2d.draw(marker);
            double markerValue = xInterval*i + min_Xcord;
            stringHeight  = g2d.getFontMetrics().getHeight();
            g2d.drawString(DF.format(markerValue), (int)(BORDER_WIDTH_INSET + i*xMarkerSpacing), (int)xAxisYPostion + stringHeight);
        }
    }

    private void paintGridLines(Graphics2D g2d){
                //drawing x axis gridlines
        g2d.setStroke(gridLineStroke);
        int noXMinorLines = 5;
        for(int i = 0;i<=noXMarkers;i++){
            for(int j = 0;j<noXMinorLines && i<noXMarkers && minorXaxisGridLines;j++){
                g2d.setColor(new Color(204,204,204));
                g2d.drawLine((int)(BORDER_WIDTH_INSET + +i*xMarkerSpacing + j*xMarkerSpacing/noXMinorLines),(int)BORDER_HEIGHT_INSET, (int)(BORDER_WIDTH_INSET + i*xMarkerSpacing+ j*xMarkerSpacing/noXMinorLines),this.getHeight()-(int)BORDER_HEIGHT_INSET);
            }

            if(majorXaxisGridLines){
                g2d.setColor(new Color(102,102,102));
                g2d.drawLine((int)(BORDER_WIDTH_INSET + i*xMarkerSpacing),(int)BORDER_HEIGHT_INSET, (int)(BORDER_WIDTH_INSET + i*xMarkerSpacing),this.getHeight()-(int)BORDER_HEIGHT_INSET);
            }
        }

        //drawing y axis gridlines
        g2d.setStroke(gridLineStroke);
        int noYMinorLines = 5;
        for(int i = 0;i<=noYMarkers;i++){
            for(int j = 0;j<=noYMinorLines && i<noXMarkers && minorYaxisGridLines;j++){
                g2d.setColor(new Color(204,204,204));
                g2d.drawLine((int)BORDER_WIDTH_INSET,(int)(BORDER_HEIGHT_INSET + i*yMarkerSpacing + j*yMarkerSpacing/noYMinorLines), this.getWidth()-(int)BORDER_WIDTH_INSET,(int)(BORDER_HEIGHT_INSET + i*yMarkerSpacing + j*yMarkerSpacing/noYMinorLines));
            }
            if(majorYaxisGridLines){
                g2d.setColor(new Color(102,102,102));
               g2d.drawLine((int)BORDER_WIDTH_INSET,(int)(BORDER_HEIGHT_INSET + i*yMarkerSpacing), this.getWidth()-(int)BORDER_WIDTH_INSET,(int)(BORDER_HEIGHT_INSET + i*yMarkerSpacing));
            }
        }
    }

    private void paintCords(Graphics2D g2d){
        //drawing graph markers
        g2d.setColor(Color.cyan);
        g2d.setStroke(cordsStroke);
        for(int i = 0;i<points.size();i++){
            switch(pointShape){
                case STAR_POINT_SHAPE:
                    Line2D[] cross = getCrossMarker(points.get(i));
                    for(int j = 0;j<cross.length;j++){
                        g2d.draw(cross[j]);
                    }
                    break;
                //case TRIANGLE_MARKER_SHAPE:
                    //g2d.draw(this.getTriangleMarker(i));
                    //break;
                case CIRCLE_POINT_SHAPE:
                    g2d.draw(this.getCircleMarker(points.get(i)));
                    break;
            }

        }
    }

    private void initGraphStats(){
        if(!fixedYAxis){
            min_Ycord = findMinNumber(this.getYCords(points));
            max_Ycord = findMaxNumber(this.getYCords(points));
            yRange = max_Ycord - min_Ycord;
        }

        if(!fixedXAxis){
            min_Xcord = findMinNumber(this.getXCords(points));
            max_Xcord = findMaxNumber(this.getXCords(points));
            xRange = max_Xcord - min_Xcord;
        }


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphicalXaxisLength = this.getWidth() - 2*BORDER_WIDTH_INSET;
        graphicalYaxisLength = this.getHeight() - 2*BORDER_HEIGHT_INSET;

        xMarkerSpacing =  graphicalXaxisLength/noXMarkers;
        yMarkerSpacing =  graphicalYaxisLength/noYMarkers;

        //finding axis positions
        if(this.points.size() > 0){
            Point2D axisIntercept = this.getGraphicalPoint(new Point2D.Double(0,0));
            xAxisYPostion = axisIntercept.getY();
            yAxisXPostion = axisIntercept.getX();
        }else{
            yAxisXPostion = BORDER_WIDTH_INSET;
            xAxisYPostion = this.getHeight() - BORDER_HEIGHT_INSET;
        }


        paintAxis(g2d);
        paintLabels(g2d);

        paintGridLines(g2d);
        paintMarkers(g2d);
        paintCords(g2d);


        //draw border
        g2d.setColor(Color.WHITE);
        g2d.setStroke(borderStroke);
        g2d.draw(new Rectangle2D.Double(BORDER_WIDTH_INSET/2,BORDER_HEIGHT_INSET/2,graphicalXaxisLength+BORDER_WIDTH_INSET,graphicalYaxisLength+BORDER_HEIGHT_INSET));

        //to draw line graph
        g2d.setColor(Color.BLUE);
        g2d.setStroke(lineGraphStroke);
        if(points != null && points.size() >0 && this.graphType == LINE_GRAPH_TYPE){
            Path2D lineGraph = getLineGraph();
            g2d.draw(lineGraph);
        }
    }


    private Line2D[] getCrossMarker(Point2D point){
            int noOfCrossLines = 4;
            Line2D[] cross = new Line2D[noOfCrossLines];
            double crossLength = pointDiameter/2;
            Point2D graphicalPoint = this.getGraphicalPoint(point);
            Point2D centerPoint = new Point2D.Double(graphicalPoint.getX(),graphicalPoint.getY());
            double angle = 360/noOfCrossLines;
            double currentAngle = 0;
            Point2D point2;
            for(int j = 0;j<noOfCrossLines;j++){
                double x2 = graphicalPoint.getX() +crossLength*Math.cos(Math.toRadians(currentAngle));
                double y2 = graphicalPoint.getY() + crossLength*Math.sin(Math.toRadians(currentAngle));
                point2 = new Point2D.Double(x2,y2);
                cross[j] = new Line2D.Double(centerPoint,point2);
                currentAngle+=angle;
            }
            return cross;
    }

    private Ellipse2D getCircleMarker(Point2D point){
            Point2D graphicalPoint = this.getGraphicalPoint(point);
            Ellipse2D circle = new Ellipse2D.Double(graphicalPoint.getX() -pointDiameter/2,graphicalPoint.getY()-pointDiameter/2,pointDiameter,pointDiameter);
            return circle;
    }

    private Path2D getLineGraph(){
        Path2D lineGraph = new Path2D.Double();
        Point2D graphicalPoint = null;
        for(int i = 0;i<this.points.size();i++){
            graphicalPoint = this.getGraphicalPoint(points.get(i));
            if(i==0){
                lineGraph.moveTo(graphicalPoint.getX(), graphicalPoint.getY());
            }else{
                lineGraph.lineTo(graphicalPoint.getX(), graphicalPoint.getY());
            }
        }
        return lineGraph;
    }


    private Point2D getGraphicalPoint(Point2D point){
        double x = BORDER_WIDTH_INSET + ((point.getX()-min_Xcord)/xRange)*graphicalXaxisLength;
        double y = this.getHeight() - ((point.getY()-min_Ycord)/yRange)*(graphicalYaxisLength) - BORDER_HEIGHT_INSET ;
        return new Point2D.Double(x,y);
    }

    public void addPoint(double x, double y){
        this.points.add(new Point2D.Double(x,y));
        initGraphStats();
        this.repaint();
    }

    public void addPoint(Point2D point){
        this.points.add(point);
        initGraphStats();
        this.repaint();
    }

    public void setXAxisLimits(double min, double max){
        this.fixedXAxis = true;
        min_Xcord = min;
        max_Xcord = max;
        xRange = max_Xcord - min_Xcord;
        repaint();
    }

    public void setYAxisLimits(double min, double max){
        this.fixedYAxis = true;
        min_Ycord = min;
        max_Ycord = max;
        yRange = max_Ycord - min_Ycord;
        repaint();
    }

    /*public void setFixedXAxis(boolean fixedXAxis) {
        this.fixedXAxis = fixedXAxis;
    }

    public void setFixedYAxis(boolean fixedYAxis) {
        this.fixedYAxis = fixedYAxis;
    }*/

    public void setPoints(ArrayList<Point2D> points) {
        this.points = points;
        initGraphStats();
        repaint();
    }


        /**
     *
     * @param gt
     *
     * Should be either
     * Graph.LINE_GRAPH_TYPE
     * Graph.SCATTER_GRAPH_TYPE
     */
    public void setGraphType(int gt){
        this.graphType = gt;
    }

    /**
     * Should be either
     * Graph.STAR_POINT_SHAPE
     * Graph.CIRCLE_POINT_SHAPE
     *
     * @param markerShape
     */
    public void setMarkerShape(int markerShape) {
        this.pointShape = markerShape;
    }

    public void setMinorYaxisGridLines(boolean minorYaxisGridLines) {
        this.minorYaxisGridLines = minorYaxisGridLines;
    }

    public void setMinorXaxisGridLines(boolean minorXaxisGridLines) {
        this.minorXaxisGridLines = minorXaxisGridLines;
    }

    public void setMajorYaxisGridLines(boolean majorYaxisGridLines) {
        this.majorYaxisGridLines = majorYaxisGridLines;
    }

    public void setMajorXaxisGridLines(boolean majorXaxisGridLines) {
        this.majorXaxisGridLines = majorXaxisGridLines;
    }

    /**
     * Rests cordinates arrays
     */
    public void clear(){
        this.points.clear();
        this.repaint();
    }

}//end of class


