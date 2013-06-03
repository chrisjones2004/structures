package trussoptimizater.Truss.Materials;

public class Steel extends Material {

    /**
     * Default youngs modulus used if preference has not been set/found. Measured in KN/m^2
     */
    public static final double DEFAULT_YOUNG_MODULUS = 205000000;
    /**
     * Default yield strength used if preference has not been set/found. Measure in KN/mm^2
     */
    public static final double DEFAULT_YIELD_STRENGTH = 0.275;
    /**
     * Default gravity used if preference has not been set/found.Measured in m/s^2
     */
    public static final double DEFAULT_GRAVITY = 9.81;
    /**
     * Default denisty used if preference has not been set/found. Measured in Kg/m^3
     */
    public static final double DEFAULT_DENSITY = 7850;
    /**
     * Default Poisson ratio used if preference has not been set/found
     */
    public static final double DEFAULT_POSSIONS_RATIO = 0.3;
    /**
     * Default Shear Modulus used if preference has not been set/found. Measured in KN/m^2
     */
    public static final double DEFAULT_SHEAR_MODULUS = 80000000;
    public static final String MATERIAL_NAME = "STEEL";

    public Steel() {
        super(MATERIAL_NAME);
        youngsModulus = prefs.getDouble(YOUNG_MODULUS_PREF_KEY, DEFAULT_YOUNG_MODULUS);
        yieldStrength = prefs.getDouble(YIELD_STRENGTH_PREF_KEY, DEFAULT_YIELD_STRENGTH);
        density = prefs.getDouble(DENSITY_PREF_KEY, DEFAULT_DENSITY);
        gravity = prefs.getDouble(GRAVITY_PREF_KEY, DEFAULT_GRAVITY);
        poissonsRatio = prefs.getDouble(POSSIONS_RATIO_PREF_KEY, DEFAULT_POSSIONS_RATIO);
        shearModulus = prefs.getDouble(SHEAR_MODULUS_PREF_KEY, DEFAULT_SHEAR_MODULUS);
    }
}
