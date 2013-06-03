package trussoptimizater.Truss.Sections;


public class SHS extends TubularSection {
    /**
     * <p>
     * The section size should have the following format "D x D"
     * <\p>
     * where D - width and depth in mm
     *
     */
    private String sectionSize;

    /**
     * Depth and bredth of section in mm
     */
    private double D;
    
    public SHS(String section,double D,double t,double area, double Ixx){
        super(area,Ixx,t,D,"SHS "+section);
        this.sectionSize = section;
        this.D = D;
    }

    /**
     *
     * @return section depth in mm
     */
    public double getDepth() {
        return D;
    }
    
    /**
     * 
     * @return section bredth in mm
     */
    public double getBredth(){
        return D;
    }

    /**
     * For example if section was a SHS 30 x 30, this method would return "30 x 30"
     * @return SHS section size in mm
     */
    public String getSectionSize() {
        return sectionSize;
    }

    /*public void print(){
        System.out.println("Section: "+section+" D: "+D+" t: "+super.getThickness()+ " Area: "+super.getArea()+" I: "+super.getIxx());
    }*/

}//end of CHS class