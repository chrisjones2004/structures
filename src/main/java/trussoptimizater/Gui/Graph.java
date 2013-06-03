package trussoptimizater.Gui;

import java.awt.*;
import javax.swing.*;
import java.text.*;


public class Graph extends JPanel { 


    private Graphics2D g2d;
    private final Stroke axisStroke = new BasicStroke(2);
    private final Stroke graphStroke = new BasicStroke(2);
    private final Stroke labelsStroke = new BasicStroke(1);
 
    private double[] xCords = new double[0];
    private double[] yCords = new double[0];
    
    private String XAxisTitle;
    private String YAxisTitle;
    
    public static final Dimension PANEL_DIMENSION = new Dimension(400,150);
    /*width eitherside of beam to edge of gui*/
    private static final int NOMINAL_WIDTH = 55;       
    private static final int NOMINAL_HEIGHT = 20; 
    
    private DecimalFormat DF = new DecimalFormat("#.##");

    double max_Xcord = Integer.MIN_VALUE;
    double max_Ycord = Integer.MIN_VALUE;
    double maxPosY = 0;
    double maxNegY = 0;

    
    private int graphicalXaxisLength;
    private int graphicalYaxisLength;

    public Graph(String gt, String xt, String yt){
        super();
        this.XAxisTitle = xt;
        this.YAxisTitle = yt;
        this.setBackground(Color.DARK_GRAY);
        this.setPreferredSize(PANEL_DIMENSION); 
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE,1));
    }

    public void setXcords(double[] x){
        this.xCords = convertToGraphXCords(x);
    }
    public void setYcords(double[] y){
        this.yCords = convertToGraphYCords(y);
    }
    public void setCords(double[] x,double[] y){
        this.xCords = x;
        this.yCords = y;
    }

    
    public double findABSMaxNumber(double[] nos){
        double maxValue = Double.MIN_VALUE;
        double minValue = Double.MAX_VALUE;
        if(nos == null){
            return 0;
        }
        
        for(int i = 0;i<nos.length;i++){
            if(nos[i] > maxValue){
                maxValue = nos[i];
            }
            if(nos[i]<minValue){
                minValue = nos[i];
            }
        }//end of for
        
        if(maxValue>-minValue){
            //System.out.println("max value is "+maxValue);
            return maxValue;  
        }else{
            //System.out.println("max value is "+minValue);
            return -minValue;
            
        }
    }//end of getActivityParemeters*/
    
    @Override
    public void paintComponent(Graphics g) {
        clear(g);
        max_Ycord = findABSMaxNumber(yCords);
        max_Xcord = findABSMaxNumber(xCords);
        calculateMaxPosMaxNegYValues();
        g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Composite standardComposite = g2d.getComposite();
        graphicalXaxisLength = this.getWidth() - 2*NOMINAL_WIDTH;
        graphicalYaxisLength = this.getHeight() - 2*NOMINAL_HEIGHT;
        
        g2d.setColor(Color.RED);
        g2d.setStroke(labelsStroke);
        String maxPos = DF.format(maxPosY);
        String maxNeg = DF.format(maxNegY);
        if (maxPosY!= Integer.MIN_VALUE && ! maxPos.equals("0")){
            g2d.drawString( maxPos , NOMINAL_WIDTH - 8* maxPos.length(), this.getHeight()-graphicalYaxisLength - 10);
        }
        if (maxNegY!= Integer.MIN_VALUE && !maxNeg.equals("0")){
            g2d.drawString(maxNeg , NOMINAL_WIDTH - 8*(maxNeg.length()-1), this.getHeight()-NOMINAL_HEIGHT );
        }
        
        g2d.drawString(DF.format(max_Xcord) , this.getWidth()-NOMINAL_WIDTH - 10, this.getHeight()/2+15 );
        

        //Painting coords
        g2d.setStroke(graphStroke);
        

        //to draw unfilled lines
        g2d.setColor(Color.BLUE);
        g2d.setStroke(labelsStroke);
        if(xCords != null && xCords.length >0){
            g2d.drawPolygon(graphicalXcords(xCords), graphicalYcords(yCords), xCords.length);
        }
        
        //to draw filled
        Color c = new Color(0.1f, 0.1f, 1.0f, 0.4f);
        g2d.setColor(c);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        if(xCords != null && xCords.length >0){
            g2d.fillPolygon(graphicalXcords(xCords), graphicalYcords(yCords), xCords.length);
        }

        //painting x axis
        g2d.setComposite(standardComposite);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(axisStroke);
        g2d.drawLine(NOMINAL_WIDTH, this.getHeight()/2, this.getWidth()-NOMINAL_WIDTH, this.getHeight()/2);
        //painting y axis
        g2d.drawLine(NOMINAL_WIDTH, NOMINAL_HEIGHT, NOMINAL_WIDTH, this.getHeight()-NOMINAL_HEIGHT);
        
        //drawign axis titles
        g2d.setColor(Color.WHITE);
        g2d.setStroke(labelsStroke);
        String[] titleWords = YAxisTitle.split(" ");
        int charSpace = 15;
        for(int i = 0;i<titleWords.length;i++){
            g.drawString(titleWords[i], (NOMINAL_WIDTH -50), this.getHeight()/4 + i*charSpace);
        }
        g2d.drawString("0", (NOMINAL_WIDTH -8), this.getHeight()/2);
        g2d.drawString(XAxisTitle, this.getWidth()/2+10, this.getHeight()/2+15);//+13 
    }
    

    public void calculateMaxPosMaxNegYValues(){
        maxPosY = 0;
        maxNegY = 0;
        
        for(int i=0;i<yCords.length;i++){
            if(yCords[i]>maxPosY){
                maxPosY = yCords[i];
            }
            
            if(yCords[i]<maxNegY){
                maxNegY = yCords[i];
            }
        }
    }
      
    public int[] graphicalXcords(double[] xCords){
        double xcord;
        int[] graphicalXCords = new int [xCords.length];

        for(int i = 0;i<xCords.length;i++){
            xcord = xCords[i];
            xcord = NOMINAL_WIDTH + (xcord/max_Xcord)*graphicalXaxisLength;
            graphicalXCords[i] = (int)xcord;
        }
        return graphicalXCords;
    }
    
    public int[] graphicalYcords(double[] yCords){
        double ycord;
        int[] graphicalYCords = new int [yCords.length];
        for(int i = 0;i<yCords.length;i++){
            if(max_Ycord>-0.00001&& max_Ycord<0.00001){
                graphicalYCords[i] = this.getHeight()/2;
            }else{
                ycord = yCords[i];
                ycord = this.getHeight()/2 - (ycord/max_Ycord)*(graphicalYaxisLength/2);
                graphicalYCords[i] = (int)ycord; 
            }

        }
        return graphicalYCords;
    }
    
    /*
     These methods just add 0 to the beginning of the array and repeat the final x axis so that
     * a fill shape in the graph can be drawn
     * */
    private double[] convertToGraphXCords(double[] a){
         double[] xCords = new double[a.length+2];
         xCords[0] = 0;
         for(int i =1;i<a.length+1;i++){
             xCords[i] = a[i-1];
         }
         xCords[xCords.length-1] = a[a.length-1];
         return xCords;
        
    }
    private double[] convertToGraphYCords(double[] a){
         double[] yCords = new double[a.length+2];
         yCords[0] = 0;
         for(int i =1;i<a.length+1;i++){
             yCords[i] = a[i-1];
         }
         yCords[yCords.length-1] = 0;
        return yCords;
    }
    
    
    
     protected void clear(Graphics g) {
        super.paintComponent(g);
    }  

    

}//end of class