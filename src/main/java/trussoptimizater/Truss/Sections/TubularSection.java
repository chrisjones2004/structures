package trussoptimizater.Truss.Sections;

import java.io.Serializable;
import trussoptimizater.Truss.Elements.Element;



public class TubularSection  implements Serializable,Cloneable{
    /**
     * Area in cm^2
     */
    private double area;

    /**
     * Second moment of area in cm^4
     */
    private double Ixx;

    /**
     * Area in mm
     */
    private double thickness;

    /**
     * Diameter in mm
     */
    private double diameter;

    /**
     * Name of section, should following format - section dimensions
     * <p>
     * For example: CHS 26.2 x 3.2
     * </p>
     */
    private String name;

    public TubularSection(double area, double Ixx,double thickness, double diameter, String name) {
        this.area = area;
        this.Ixx = Ixx;
        this.thickness = thickness;
        this.diameter = diameter;
        this.name = name;
    }

    /**
     *
     * @return String reprenstation of section object
     */
    @Override
    public String toString(){
        return "[name, diameter, thickness, area, Ixx ] - ["+this.name+", "+this.diameter+"mm, "+this.thickness+"mm ,"+this.area+"cm^2 ,"+this.Ixx+"mm^4 ]";
    }
    
    /**
     * 
     * @return Section Area in cm^2
     */
    public double getArea() {
        return area;
    }
    /**
     * 
     * @return second moment of area around major axis in cm^4
     */
    public double getIxx() {
        return Ixx;

    }

    /**
     *
     * @return clone of Section object
     * @throws CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return (TubularSection)super.clone();
    }

    /**
     *
     * @return Section Name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return Section thickness in mm
     */
    public double getThickness() {
        return thickness;
    }

    /**
     * Used for joint analysis
     * Is not a good class attribute as not all sections
     * have a diameter
     * @return Diameter in mm
     */
    public double getDiameter() {
        return diameter;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}