package trussoptimizater.Truss.Sections;

/**
 * This class represents a circle hollow section (CHS)
 * @author Chris
 */
public final class CHS extends TubularSection {
    /**
     * <p>
     * The section size should have the following format "D x t"
     * <\p>
     * where
     * <ul>
     * <li>D - Diameter in mm
     * <li>t - thickness in mm
     * </ul>
     *
     */
    private String sectionSize;

    /**
     * Diameter of Section in mm
     */
    private double D;


    public CHS(String section, double D,double t,double area, double Ixx){
        super(area,Ixx,t,D,"CHS "+section);
        this.sectionSize = section;
        this.D = D;
    }

    /**
     *
     * @return section diameter in mm
     */
    @Override
    public double getDiameter() {
        return D;
    }

    /**
     * For example if section was a CHS 26.9 x 3.2, this method would return "26.9 x 3.2"
     * @return CHS section size in mm
     */
    public String getSectionSize() {
        return sectionSize;
    }

    
    /*public void print(){
        System.out.println("Section: "+section+" D: "+D+" Area: "+getArea()+" I: "+super.getIxx());
    }*/

}//end of CHS class