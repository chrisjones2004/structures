package trussoptimizater.Truss.Materials;

import java.io.Serializable;
import java.util.prefs.Preferences;


public abstract class Material implements Serializable , Cloneable{

    /**
     * Prefereneces is used to store selected values
     */
    transient protected Preferences prefs;
    public static final String YOUNG_MODULUS_PREF_KEY = "YOUNG_MODULUS_PREF_KEY";
    public static final String YIELD_STRENGTH_PREF_KEY = "YIELD_STRENGTH_PREF_KEY";
    public static final String GRAVITY_PREF_KEY = "GRAVITY_PREF_KEY ";
    public static final String DENSITY_PREF_KEY = "DENSITY_PREF_KEY";
    public static final String POSSIONS_RATIO_PREF_KEY = "POSSIONS_RATIO_PREF_KEY";
    public static final String SHEAR_MODULUS_PREF_KEY = "SHEAR_MODULUS_PREF_KEY";

    protected String materialName;

    /**
     * Poisson ratio
     */
    protected double poissonsRatio;
    /**
     * Shear modulus of section in KN/m^2
     */
    protected double shearModulus;

    /**
     * Youngs modulus of steel. Measured in KN/m^2
     */
    protected double youngsModulus;

    /**
     * Yield strength for structural hollow sections see Eurocode Table 3.1. Measured in KN/mm^2
     *
     */
    protected double yieldStrength;
    /**
     * Material density is used in calculation of truss weight. Measured in Kg/m^3
     */
    protected double density;
    /**
     * Gravity used for calculating weight of truss. Measured in m/s^2
     */
    protected double gravity;


    public Material(String materialName){
        this.materialName = materialName;
        prefs = Preferences.userNodeForPackage(this.getClass());
    }


    /**
     *
     * @return Name of material
     */
    public String getMaterialName() {
        return materialName;
    }


    /**
     *
     * @return Density of Material (p) of material in Kg/m^3
     */
    public double getDensity() {
        return density;
    }

    /**
     *
     * @return Young Modulus (E) of material in KN/m^2
     */
    public double getYoungsModulus() {
        return youngsModulus;
    }

    /**
     * It does not seem very appropriate to have gravity as an attribute of a material, prob should move
     * @return Gravity - should be 9.81
     */
    public double getGravity() {
        return gravity;
    }

    /**
     *
     * @return Material yield strength in KN/mm^2
     */
    public double getYieldStrength() {
        return yieldStrength;
    }

    /**
     * Currently shear modulus is not used for any calculations
     * @return Shear modulus
     */
    public double getShearModulus() {
        return shearModulus;
    }

    /**
     * Currently poisson ratio is not used for any calculations
     * @return Poisson ratio for material
     */
    public double getPoissonsRatio() {
        return poissonsRatio;
    }


    /**
     *
     * @param density Material density in Kg/m^3
     */
    public void setDensity(double density) {
        this.density = density;
        prefs.put(DENSITY_PREF_KEY, Double.toString(density));
    }

    /**
     *
     * @param youngsModulus Young Modulus (E) of material in KN/m^2
     */
    public void setYoungsModulus(double youngsModulus) {
        this.youngsModulus = youngsModulus;
        prefs.put(YOUNG_MODULUS_PREF_KEY, Double.toString(youngsModulus));
    }

    /**
     * Again, graivty should not be in this class, need to move this attribute else where
     * @param gravity
     */
    public void setGravity(double gravity) {
        this.gravity = gravity;
        prefs.put(GRAVITY_PREF_KEY, Double.toString(gravity));
    }

    /**
     *
     * @param yieldStrength Yield strength of material in KN/mm^2
     */
    public void setYieldStrength(double yieldStrength) {
        this.yieldStrength = yieldStrength;
        prefs.put(YIELD_STRENGTH_PREF_KEY, Double.toString(yieldStrength));
    }

    /**
     * Currently poisson ratio is not used for any calculations, therefore no need to use this method
     * @param poissonsRatio Poisson ratio of material
     */
    public void setPoissonsRatio(double poissonsRatio) {
        this.poissonsRatio = poissonsRatio;
        prefs.put(POSSIONS_RATIO_PREF_KEY, Double.toString(poissonsRatio));
    }

    /**
     * Currently shear modulus is not used for any calculations, therefore no need to use this method
     * @param shearModulus Shear Modulus of material
     */
    public void setShearModulus(double shearModulus) {
        this.shearModulus = shearModulus;
        prefs.put(SHEAR_MODULUS_PREF_KEY, Double.toString(shearModulus));
    }

    /**
     *
     * @param materialName Name that this material will be refered to by
     */
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Material)super.clone();
    }
}
